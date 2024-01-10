package com.beeecommerce.service.impl;

import com.beeecommerce.entity.*;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.mapper.AddressMapper;
import com.beeecommerce.mapper.LocalityMapper;
import com.beeecommerce.model.dto.AddressDto;
import com.beeecommerce.model.dto.LocalityDto;
import com.beeecommerce.model.dto.OrderDto;
import com.beeecommerce.model.dto.WardDetailDto;
import com.beeecommerce.repository.*;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceIml implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final WardRepository wardRepository;
    private final ProvinceRepository provinceRepository;
    private final LocalityMapper localityMapper;
    private final DistrictRepository districtRepository;

    public Page<AddressDto> findByUser(Long userId, Pageable pageable) {
        return addressRepository.findByUser(userId, pageable).map(addressMapper);
    }

    @Override
    public Page<AddressDto> getAll(Pageable pageable) {
        Account account = accountService.getAuthenticatedAccount();

        return findByUser(account.getId(), pageable);
    }

    @Override
    public void createAddress(AddressDto addressDto) {
        try {
            Address address = new Address();

            Ward ward = wardRepository.findById(addressDto.getWardId()).orElse(null);

            Account account = accountService.getAuthenticatedAccount();
            address.setUser(account);

            address.setReceiverPhone(addressDto.getReceiverPhone());
            address.setReceiverName(addressDto.getReceiverName());
            address.setNote(addressDto.getNote());
            address.setStreet(addressDto.getStreet());

            // BeanUtils.copyProperties(addressDto, address);
            //address.setUser(accountService.getAuthenticatedAccount());

            address.setWard(ward);

            addressRepository.save(address);
        } catch (Exception e) {
        }
    }

    @Override
    public void update(AddressDto addressDto) throws ParamInvalidException {
        Address address = addressRepository.findById(addressDto.getId()).orElse(null);

        if (address != null) {

            address.setReceiverName(addressDto.getReceiverName());
            address.setReceiverPhone(addressDto.getReceiverPhone());
            address.setNote(addressDto.getNote());

            Ward ward = new Ward();
            ward.setId(addressDto.getWardId());
            address.setWard(ward);

            addressRepository.save(address);
        } else {
            throw new ParamInvalidException("Address not found");
        }
    }


    @Override
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public Address getById(Long addressId) throws EntityNotFoundException {
        return addressRepository
                .findById(addressId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Address not found")
                );

    }

    @Override
    public List<LocalityDto> getAllProvince() {
        return provinceRepository
                .findAll()
                .stream()
                .map(localityMapper)
                .toList();
    }

    @Override
    public List<LocalityDto> getAllDistrict(Long provinceId) {
        return districtRepository
                .findAllByProvinceId(provinceId)
                .stream()
                .map(localityMapper)
                .toList();
    }

    @Override
    public List<LocalityDto> getAllWard(Long districtId) {
        return wardRepository
                .findAllByDistrictId(districtId)
                .stream()
                .map(localityMapper)
                .toList();
    }

    @Override
    public WardDetailDto getWardDetail(Long wardId) throws EntityNotFoundException {
        return wardRepository
                .findById(wardId)
                .map(ward -> {
                    District district = ward.getDistrict();
                    Province province = district.getProvince();
                    return new WardDetailDto(
                            localityMapper.apply(province),
                            localityMapper.apply(district),
                            localityMapper.apply(ward)
                    );
                }).orElseThrow(
                        () -> new EntityNotFoundException("Ward not found")
                );
    }

    @Override
    public AddressDto findById(Long id){
        return addressRepository.findById(id).map(addressMapper).orElseThrow(()->
                new EntityNotFoundException("Không tìm thấy địa chỉ"));
    }
}

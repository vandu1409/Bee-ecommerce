package com.beeecommerce.facade;

import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.AddressDto;
import com.beeecommerce.model.dto.LocalityDto;
import com.beeecommerce.model.dto.WardDetailDto;
import com.beeecommerce.service.AddressService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressFacade {

    private final AddressService addressService;

    public Page<AddressDto> getAll(Pageable pageable) {
        return addressService.getAll(pageable);
    }

    public void createAddress(AddressDto addressDto) throws ParamInvalidException {
        ObjectHelper.checkNullParam(addressDto.getReceiverPhone(), addressDto.getReceiverPhone(),
                addressDto.getWardId(), addressDto.getNote());

        addressService.createAddress(addressDto);
    }

    public void update(Long id, AddressDto addressDto) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);
        ObjectHelper.checkNullParam(addressDto.getReceiverPhone(), addressDto.getReceiverPhone(),
                addressDto.getWardId(), addressDto.getUserId(), addressDto.getNote());
        addressService.update(addressDto);
    }

    public void delete(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        addressService.deleteAddress(id);
    }


    public List<LocalityDto> getAllProvince() {
        return addressService.getAllProvince();
    }

    public List<LocalityDto> getAllDistrict(Long provinceId) {
        return addressService.getAllDistrict(provinceId);
    }

    public List<LocalityDto> getAllWard(Long districtId) {
        return addressService.getAllWard(districtId);
    }

    public WardDetailDto getWardDetail(Long wardId) throws EntityNotFoundException {
        return addressService.getWardDetail(wardId);
    }

    public AddressDto findById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);
        return addressService.findById(id);
    }
}

package com.beeecommerce.service.impl;

import com.beeecommerce.constance.Role;
import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Address;
import com.beeecommerce.entity.Vendor;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.mapper.AddressMapper;
import com.beeecommerce.mapper.VendorMapper;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.repository.AccountRepository;
import com.beeecommerce.repository.AddressRepository;
import com.beeecommerce.repository.VendorRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.VendorService;
import com.beeecommerce.util.ObjectHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;

    @Override
    public Page<VendorDto> getAll(Pageable pageable) {
        return vendorRepository
                .findAll(pageable)
                .map(vendorMapper);
    }

    @Override
    @Transactional
    public void insert(VendorDto vendorDto) throws AuthenticateException {

        Vendor vendor = new Vendor();

        Address address = addressMapper.loadSimpleAddress(vendorDto);

        Account account = accountService.getAuthenticatedAccount();
        account.setRole(Role.VENDOR);


        vendor.setId(vendorDto.getId());
        vendor.setName(vendorDto.getName());
        vendor.setRegisterAt(LocalDate.now());
        vendor.setUser(account);
        vendor.setAddress(address);

        addressRepository.save(address);
        vendorRepository.save(vendor);
        accountRepository.save(account);
    }

    @Override
    public void update(VendorDto vendorDto) throws EntityNotFoundException {
        Vendor vendor = vendorRepository.findById(vendorDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy người bán nào có id " + vendorDto.getId()));

        if (!ObjectHelper.checkNull(vendor.getName())) {
            vendor.setName(vendorDto.getName());
        }
        vendorRepository.save(vendor);
    }

    @Override
    public void delete(Long id) {
        vendorRepository.deleteById(id);
    }

    @Override
    public VendorDto getVendor(Long id) {
        return vendorRepository
                .findById(id)
                .map(vendorMapper)
                .orElse(null);
    }
}

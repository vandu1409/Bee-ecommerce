package com.beeecommerce.service;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.model.dto.VendorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorService {
    Page<VendorDto> getAll(Pageable pageable);

    void insert(VendorDto vendorDto) throws AuthenticateException;

    void update(VendorDto vendorDto) throws EntityNotFoundException;

    void delete(Long id);

    VendorDto getVendor(Long id);
}

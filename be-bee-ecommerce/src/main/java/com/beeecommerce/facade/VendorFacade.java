package com.beeecommerce.facade;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.repository.VendorRepository;
import com.beeecommerce.service.VendorService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorFacade {

    private final VendorService vendorService;
    private final VendorRepository vendorRepository;

    public Page<VendorDto> getVendors(Pageable pageable) {
        return vendorService.getAll(pageable);
    }

    public VendorDto getVendor(Long id) throws ParamInvalidException {
        if (ObjectHelper.checkNull(id)) {
            throw new ParamInvalidException("Id truyền vào rỗng!");
        }
        return vendorService.getVendor(id);
    }

    public void insert(VendorDto vendorDto) throws AuthenticateException {
        vendorService.insert(vendorDto);
    }

    public void update(Long id, VendorDto vendorDto) throws ParamInvalidException, EntityNotFoundException {
        if (!ObjectHelper.checkNull(id)) {
            throw new ParamInvalidException("Id truyền vào ỗng!");
        }

        vendorDto.setId(id);
        vendorService.update(vendorDto);
    }

    public void delete(Long id) throws ParamInvalidException {
        if (ObjectHelper.checkNull(id)) {
            throw new ParamInvalidException("Id truyền vào rỗng!");
        }
        vendorService.delete(id);
    }
}

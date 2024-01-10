package com.beeecommerce.facade;

import com.beeecommerce.entity.Brand;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.BrandDto;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.service.BrandService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandFacade {
    private final BrandService brandService;


    public Page<BrandDto> getAll(Pageable pageable) {
        return brandService.getAll(pageable);
    }

    public void insert(BrandDto brandDto, MultipartFile image) throws ApplicationException {
        brandService.insert(brandDto,image);
    }

    public BrandDto update(Long id, BrandDto brandDto,MultipartFile image) throws EntityNotFoundException, ApplicationException {
        checkIdNull(id);
        brandDto.setId(id);
        return brandService.update(brandDto,image);
    }

    private void checkIdNull(Long id) throws ParamInvalidException {
        if (id == null) {
            throw new ParamInvalidException("Brand not found");
        }
    }

    public BrandDto delete(Long id) throws EntityNotFoundException, ParamInvalidException {
        checkIdNull(id);
        return brandService.delete(id);
    }

    public BrandDto findById(Long id) throws ParamInvalidException {

        ObjectHelper.checkNullParam(id);

        return brandService.findById(id);


    }

}

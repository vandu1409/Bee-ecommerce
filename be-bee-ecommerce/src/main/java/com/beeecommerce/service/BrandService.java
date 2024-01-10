package com.beeecommerce.service;

import com.beeecommerce.entity.Brand;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.BrandDto;
import com.beeecommerce.model.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BrandService {


    Page<BrandDto> getAll(Pageable pageable);

    void insert(BrandDto brandDto, MultipartFile image) throws ApplicationException;

    BrandDto update(BrandDto brandDto,MultipartFile image) throws EntityNotFoundException,ApplicationException;

    BrandDto delete(Long id) throws EntityNotFoundException;

    BrandDto findById(Long id);
}

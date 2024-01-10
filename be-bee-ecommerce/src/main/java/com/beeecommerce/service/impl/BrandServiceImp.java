package com.beeecommerce.service.impl;

import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.entity.Brand;
import com.beeecommerce.entity.Category;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.mapper.BrandMapper;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.BrandDto;
import com.beeecommerce.model.dto.CartDto;
import com.beeecommerce.repository.BrandRepository;
import com.beeecommerce.service.AmazonClientService;
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
public class BrandServiceImp implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    private final AmazonClientService amazonClientService;


    @Override
    public Page<BrandDto> getAll(Pageable pageable) {
        return brandRepository.findAll(pageable).map(brandMapper);
    }

    @Override
    public void insert(BrandDto brandDto, MultipartFile image) throws ApplicationException {
        Brand brand = new Brand();

        brand.setId(brandDto.getId());
        brand.setName(brandDto.getName());

        if (image != null) {
            String imageUrl = amazonClientService.uploadFile(image, TypeUpLoad.IMAGE);
            brand.setUrl(imageUrl);
        }
//        brand.setUrl(brandDto.getUrl());

        brandRepository.save(brand);
    }

    @Override
    public BrandDto update(BrandDto brandDto,MultipartFile image) throws EntityNotFoundException, ApplicationException {

        Brand brand = brandRepository.findById(brandDto.getId()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy hãng này!")
        );

        if (!ObjectHelper.checkNull(brandDto.getName())) {
            brand.setName(brandDto.getName());
        }
        if (!ObjectHelper.checkNull(brandDto.getUrl())) {
            brand.setUrl(brandDto.getUrl());
        }

        if (image != null) {
            String imageUrl = amazonClientService.uploadFile(image, TypeUpLoad.IMAGE);
            brand.setUrl(imageUrl);
        }

        brandRepository.save(brand);

        return brandMapper.apply(brand);
    }

    @Override
    public BrandDto delete(Long id) throws EntityNotFoundException {
        Brand brand = brandRepository.findById(id).orElse(null);
        if (brand != null) {
            brandRepository.delete(brand);
        } else {
            throw new EntityNotFoundException("Brand not found");
        }

        return brandMapper.apply(brand);
    }


    @Override
    public BrandDto findById(Long id) {
        return brandRepository
                .findById(id)
                .map(brandMapper)
                .orElseThrow();
    }



}

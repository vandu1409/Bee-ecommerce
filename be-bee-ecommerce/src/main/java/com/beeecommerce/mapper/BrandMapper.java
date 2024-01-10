package com.beeecommerce.mapper;

import com.beeecommerce.entity.Brand;
import com.beeecommerce.model.dto.BrandDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class BrandMapper implements Function<Brand, BrandDto> {
    @Override
    public BrandDto apply(Brand brand) {
        return BrandDto.builder()
                .id(brand.getId())
                .name(brand.getName())
                .url(brand.getUrl())
                .productCount(brand.getProducts() == null ? 0 :(long) brand.getProducts().size())
                .build();
    }
}

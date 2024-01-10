package com.beeecommerce.mapper;


import com.beeecommerce.entity.ProductTag;
import com.beeecommerce.model.dto.HashtagDto;
import com.beeecommerce.model.dto.ProductTagDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductTagMapper implements Function<ProductTag, ProductTagDto> {

    @Override
    public ProductTagDto apply(ProductTag productTag) {
        return ProductTagDto
                .builder().
                id(productTag.getId()).
                productId(productTag.getProduct().getId())
                .hashtagDto(HashtagDto.builder().id(productTag.getTag().getId()).build())
                .build();
    }
}

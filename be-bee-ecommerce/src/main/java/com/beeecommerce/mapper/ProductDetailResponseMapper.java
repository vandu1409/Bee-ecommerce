package com.beeecommerce.mapper;

import com.beeecommerce.entity.*;
import com.beeecommerce.model.core.SimpleClassify;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.model.dto.ClassifyNameDto;
import com.beeecommerce.model.dto.ProductDto;
import com.beeecommerce.model.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductDetailResponseMapper implements Function<Product, ProductDetailResponse> {

        private final AttributeMapper attributeMapper;

        private final ClassifyMapper classifyMapper;

        private final BrandMapper brandMapper;

        private final FeedbackMapper feedbackMapper;


    @Override
    @Cacheable(value = "product-detail", key = "#product.id")
    public ProductDetailResponse apply(Product product) {
        Long rootPrice = product.getClassifyGroups().isEmpty() ||
                product.getClassifyGroups().get(0).getClassifies().isEmpty()
                ? 1000l
                : product.getClassifyGroups().get(0).getClassifies().get(0).getSellPrice();

                List<AttributeDto> attributesDto = product.getProductAttributes().stream().map(item -> {
                        return AttributeDto.builder()
                                        .id(item.getAttributes().getId())
                                        .name(item.getAttributes().getName())
                                        .value(item.getValue())
                                        .build();
                }).toList();

                return ProductDetailResponse.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .rootPrice(rootPrice)
                                .poster(product.getPoster())
                                .classifyName(product.getClassifyName())
                                .classifyGroupName(product.getClassifyGroupName())
                                .brand(brandMapper.apply(product.getBrand()))
                                .categoryId(product.getCategory().getId())
                                .weight(product.getWeight())
                                .height(product.getHeight())
                                .wight(product.getWidth())
                                .length(product.getLength())
                                .description(product.getDescription())
                                .feedbacks(product.getFeedbacks()
                                                .stream()
                                                .map(feedbackMapper)
                                                .toList())
                                .classifies(product.getClassifies()
                                                .stream()
                                                .map(classifyMapper)
                                                .toList())
                                .images(product.getProductImages()
                                                .stream()
                                                .map(ProductImage::getUrl).toList())
                                .tags(product.getAllHashTag()
                                                .stream()
                                                .map(Hashtag::getName)
                                                .toList())
                                .attributes(attributesDto)
                                .build();

        }
}

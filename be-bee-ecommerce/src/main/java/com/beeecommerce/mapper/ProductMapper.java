package com.beeecommerce.mapper;

import com.beeecommerce.entity.*;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.mapper.core.BaseMapper;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.model.dto.ClassifyDto;
import com.beeecommerce.model.dto.ClassifyNameDto;
import com.beeecommerce.model.dto.ProductDto;
import com.beeecommerce.repository.BrandRepository;
import com.beeecommerce.repository.CategoryRepository;
import com.beeecommerce.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductMapper implements BaseMapper<Product, ProductDto> {

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    private final AccountService accountService;

    @Override
    public ProductDto apply(Product product) {

        List<AttributeDto> attributesDto = product.getProductAttributes().stream().map(item -> {
            return AttributeDto.builder()
                    .id(item.getAttributes().getId())
                    .name(item.getAttributes().getName())
                    .value(item.getValue())
                    .build();
        }).toList();

        List<String> fileUrls = product.getProductImages().stream().map(ProductImage::getUrl).toList();

        List<String> tags = product.getAllHashTag().stream().map(Hashtag::getName).toList();

//        String classifyGroupName =

        ClassifyNameDto classifyGroupName = ClassifyNameDto.builder()
                .name(product.getClassifyGroups().get(0).getClassifyName().getName())
                .build();

        ClassifyNameDto classifyName = ClassifyNameDto.builder()
                .name(product.getClassifies().get(0).getClassifyName().getName())
                .build();


        return ProductDto.builder()
                .id(product.getId())
                .vendorId(product.getVendors().getId())
                .name(product.getName())
                .categoryId(product.getCategory().getId())
                .brandId(product.getBrand().getId())
                .classifies(convertListClassifyDto(product))
                .attributes(attributesDto)
                .fileUrls(fileUrls)
                .tags(tags)
                .weight(product.getWeight())
                .height(product.getHeight())
                .wight(product.getWidth())
                .length(product.getLength())
                .description(product.getDescription())
                .classifyGroupName(classifyGroupName)
                .classifyName(classifyName)
                .build();
    }

    @Override
    public Product reverse(ProductDto productDto) {

        Product product = new Product();

        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setWeight(productDto.getWeight());
        product.setHeight(productDto.getHeight());
        product.setLength(productDto.getLength());
        product.setWidth(productDto.getWight());
        product.setDescription(productDto.getDescription());

        product.setBrand(brandRepository.findById(productDto.getBrandId()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy thương hiệu sản phẩm!")));
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm danh mục sản phẩm!")));

        return product;
    }

    public List<ClassifyDto> convertListClassifyDto(Product product) {
        List<ClassifyDto> classifiesDto = new ArrayList<>();

        for (ClassifyGroup group : product.getClassifyGroups()) {

            for (Classify classify : group.getClassifies()) {

                ClassifyDto classifyDto = ClassifyDto.builder()
                        .classifyGroupId(group.getId())
                        .classifyId(classify.getId())
                        .classifyValue(classify.getValue())
                        .classifyGroupValue(group.getValue())
                        .price(classify.getSellPrice())
                        .inventory(classify.getInventory())
                        .quantity(classify.getQuantity())
                        .build();

                classifiesDto.add(classifyDto);
            }
        }

        return classifiesDto;
    }
}

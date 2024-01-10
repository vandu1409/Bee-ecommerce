package com.beeecommerce.service.impl;


import com.beeecommerce.constance.TypeMethod;
import com.beeecommerce.entity.Attribute;
import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.ProductAttribute;
import com.beeecommerce.mapper.AttributeMapper;
import com.beeecommerce.model.dto.AttributeDto;
import com.beeecommerce.repository.AttributeRepository;
import com.beeecommerce.repository.ProductAttributeRepository;
import com.beeecommerce.service.ProductAttributesService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAttributesServiceImpl implements ProductAttributesService {

    private final ProductAttributeRepository productAttributeRepository;

    private final AttributeRepository attributeRepository;

    private final AttributeMapper attributeMapper;


    public ProductAttribute addToProductAttributes(AttributeDto attributeDto, Product product) {
        Attribute attribute = attributeMapper.reverse(attributeDto);

        ProductAttribute productAttribute = ProductAttribute.builder()
                .product(product)
                .attributes(attribute)
                .value(attributeDto.getValue())
                .build();


        return productAttributeRepository.save(productAttribute);
    }


    public ProductAttribute updateToProductAttributes(AttributeDto attributeDto, Product product) {

        ProductAttribute productAttribute = productAttributeRepository
                .findByProductAndAttributes(product.getId(), attributeDto.getName()).orElse(null);

        if (productAttribute == null) {
            return addToProductAttributes(attributeDto, product); // trường hợp thêm attributes mới
        }

        if (!ObjectHelper.checkNull(attributeDto.getValue())) {

            productAttribute.setValue(attributeDto.getValue());
            return productAttributeRepository.save(productAttribute);
        }

        return null;

    }


    public void removeStaleDeletedDataFromDB(List<AttributeDto> list, Product product) {
        List<ProductAttribute> productAttributes = productAttributeRepository.findByProduct(product.getId());

        List<String> attributeNames = list.stream().map(AttributeDto::getName).toList();

        List<Long> deletedProductAttributes = new ArrayList<>();

        for (ProductAttribute productAttribute : productAttributes) {
            if (!attributeNames.contains(productAttribute.getAttributes().getName())) {
                deletedProductAttributes.add(productAttribute.getId());
            }
        }

        productAttributeRepository.deleteAllById(deletedProductAttributes);

    }

    @Override
    public void saveAndUpdateListAttributes(List<AttributeDto> list, Product product, TypeMethod type) {
        for (AttributeDto item : list) {
            if (!ObjectHelper.checkNull(item.getValue(), item.getName())) {
                if (type.equals(TypeMethod.CREATE)) {
                    addToProductAttributes(item, product);
                } else if (type.equals(TypeMethod.UPDATE)) {
                    updateToProductAttributes(item, product);
                }

            }
        }

        if (type.equals(TypeMethod.UPDATE)) {
            removeStaleDeletedDataFromDB(list, product);
        }

    }


}

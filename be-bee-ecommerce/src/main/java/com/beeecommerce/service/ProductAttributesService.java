package com.beeecommerce.service;

import com.beeecommerce.constance.TypeMethod;
import com.beeecommerce.entity.Product;
import com.beeecommerce.model.dto.AttributeDto;

import java.util.List;

public interface ProductAttributesService {
    void saveAndUpdateListAttributes(List<AttributeDto> list, Product product, TypeMethod type);
}

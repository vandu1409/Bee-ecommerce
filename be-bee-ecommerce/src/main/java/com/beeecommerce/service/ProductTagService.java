package com.beeecommerce.service;

import com.beeecommerce.constance.TypeMethod;
import com.beeecommerce.entity.Product;
import com.beeecommerce.model.dto.ProductTagDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductTagService {
    Page<ProductTagDto> getAllProductTag(Pageable pageable);

    ProductTagDto getProductTagById(Long id);

//    void createProductTag(ProductTagDto productTagDto);

    void deleteProductTag(Long id);

    void saveAndUpdateListTag(List<String> tags, Product product, TypeMethod type);

//    public void update(Long id, ProductTagDto productTagDto);
}
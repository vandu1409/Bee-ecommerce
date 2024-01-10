package com.beeecommerce.mapper;

import com.beeecommerce.document.ProductDocument;
import com.beeecommerce.model.dto.ProductDocumentDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDocumentMapper implements Function<ProductDocument, ProductDocumentDto> {
    @Override
    public ProductDocumentDto apply(ProductDocument productDocument) {
        return ProductDocumentDto
                .builder()
                .id(productDocument.getId())
                .name(productDocument.getName())
                .price(productDocument.getPrice())
                .build();
    }
}

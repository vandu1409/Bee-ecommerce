package com.beeecommerce.service.impl;

import com.beeecommerce.document.ProductDocument;
import com.beeecommerce.entity.Product;
import com.beeecommerce.mapper.ProductDocumentMapper;
import com.beeecommerce.mapper.SimpleProductResponseMapper;
import com.beeecommerce.model.dto.ProductDocumentDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.repository.ProductRepository;
import com.beeecommerce.repository.elastic.ProductDocumentRepository;
import com.beeecommerce.service.ProductDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductDocumentServiceImpl implements ProductDocumentService {

    private final ProductDocumentRepository productDocumentRepository;

    private final ProductDocumentMapper productDocumentMapper;

    private final ProductRepository productRepository;

    private final SimpleProductResponseMapper simpleProductResponseMapper;

    @Override
    public Page<SimpleProductResponse> findByKeyword(String keyword, Pageable pageable) {

        Page<ProductDocument> page = productDocumentRepository.findByKeyword(keyword, pageable);

        List<Long> ids = page
                .map(ProductDocument::getId)
                .getContent()
                .stream()
                .collect(Collectors.toList());


        List<Product> listProducts = productRepository.findAllById(ids);

        Page<SimpleProductResponse> productPage = new PageImpl<>(
                listProducts, pageable, page.getTotalElements()).map(simpleProductResponseMapper);

        return productPage;
    }




}

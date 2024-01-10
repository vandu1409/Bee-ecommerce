package com.beeecommerce.service.impl;

import com.beeecommerce.document.ProductDocument;
import com.beeecommerce.entity.Hashtag;
import com.beeecommerce.entity.Product;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.mapper.SimpleProductResponseMapper;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.repository.ProductRepository;
import com.beeecommerce.repository.elastic.ProductDocumentRepository;
import com.beeecommerce.service.ProductPersonalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPersonalServiceImpl implements ProductPersonalService {

    private final ProductRepository productRepository;

    private final ProductDocumentRepository productDocumentRepository;
    private final SimpleProductResponseMapper simpleProductResponseMapper;

    @Override
    @Transactional
    public Page<SimpleProductResponse> suggestProduct(Pageable pageable) {
        return productRepository
                .randomProduct(pageable)
                .map(simpleProductResponseMapper);
    }

    @Override
    public Page<SimpleProductResponse> findSimilarProduct(Long id, Pageable pageable) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy sản phẩm"));


        String tags = product.getAllHashTag().stream()
                .map(Hashtag::getName)
                .collect(Collectors.joining(" "));

//        System.err.println(tags);
//
//        ProductDocument productDocument = ProductDocument
//                .builder()
//                .id(product.getId())
//                .name(product.getName())
//                .categoryname(product.getCategory().getName())
//                .tags(tags)
//                .build();

        String keyword = "%s %s %s %s".formatted(product.getName(),product.getCategory().getName(),product.getBrand().getName(),tags);

        Page<ProductDocument> page = productDocumentRepository.findByKeyword(keyword,pageable);


        System.err.println(page.getContent().size());

        page.getContent().forEach(item->{
            System.err.println(item.getName());
        });

        List<Long> ids = page
                .map(ProductDocument::getId)
                .getContent()
                .stream()
                .filter(item -> item != product.getId())
                .collect(Collectors.toList());


        List<Product> listProducts = productRepository.findAllById(ids);

        if(listProducts!=null){
            if(listProducts.size()<pageable.getPageSize()){
                List<Product> listRandom = productRepository
                        .randomProductByTop(pageable.getPageSize()-listProducts.size());

                listProducts.addAll(listRandom);
            }
        }



        Page<SimpleProductResponse> productPage = new PageImpl<>(
                listProducts, pageable, page.getTotalElements()).map(simpleProductResponseMapper);

        return productPage;

    }
}

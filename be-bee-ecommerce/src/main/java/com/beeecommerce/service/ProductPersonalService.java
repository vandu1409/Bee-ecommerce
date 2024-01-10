package com.beeecommerce.service;

import com.beeecommerce.model.response.SimpleProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductPersonalService {

    Page<SimpleProductResponse> suggestProduct(Pageable pageable);

    Page<SimpleProductResponse> findSimilarProduct(Long id, Pageable pageable);
}

package com.beeecommerce.service;

import com.beeecommerce.document.ProductDocument;
import com.beeecommerce.model.dto.ProductDocumentDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductDocumentService {

    public Page<SimpleProductResponse> findByKeyword(String name, Pageable pageable) ;


}

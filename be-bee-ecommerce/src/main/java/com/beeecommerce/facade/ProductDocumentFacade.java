package com.beeecommerce.facade;

import com.beeecommerce.document.ProductDocument;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.ProductDocumentDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.service.ProductDocumentService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDocumentFacade {

    private final ProductDocumentService productDocumentService;

    public Page<SimpleProductResponse> findByKeyword(String keyword, Pageable pageable) throws ParamInvalidException {

        ObjectHelper.checkNullParam(keyword);

        return  productDocumentService.findByKeyword(keyword,pageable);
    }
}

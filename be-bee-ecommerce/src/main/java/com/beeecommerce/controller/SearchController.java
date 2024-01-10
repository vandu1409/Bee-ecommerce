package com.beeecommerce.controller;


import com.beeecommerce.document.ProductDocument;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.ProductDocumentFacade;
import com.beeecommerce.model.dto.ProductDocumentDto;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.beeecommerce.repository.elastic.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.SearchPath.SEARCH_SUFFIX;

@RestController
@RequestMapping(SEARCH_SUFFIX)
@RequiredArgsConstructor
public class SearchController {

    private final ProductDocumentFacade productDocumentFacade;

    private final ProductDocumentRepository productDocumentRepository;

    @GetMapping()
    public ResponseObject<?> search (
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam("keyword") String keyword
    ) throws ParamInvalidException {

        System.err.println("Keyword : "+keyword);

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<SimpleProductResponse> productDocuments = productDocumentFacade.findByKeyword(keyword,pageable);
        return ResponseHandler.response(productDocuments);
    }

    @GetMapping("getAll")
    public  ResponseObject<List<ProductDocument>> getAll(){
        Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<ProductDocument> list = productDocumentRepository.findAll(pageable);

        for (ProductDocument item:list.getContent()
             ) {
            System.err.println(item.getCategoryname());
        }
        return  ResponseHandler.response(list);
    }
}

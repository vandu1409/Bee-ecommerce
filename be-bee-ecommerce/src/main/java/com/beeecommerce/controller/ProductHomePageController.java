package com.beeecommerce.controller;

import com.beeecommerce.facade.ProductFacade;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.ProductPath.PRODUCT_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCT_PREFIX)
public class ProductHomePageController {

    private final ProductFacade productFacade;

    @GetMapping("/home/suggest")
    public ResponseObject<List<SimpleProductResponse>> suggestProduct(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "45") Integer size
                ) {
        return ResponseHandler.response(
                productFacade.suggestProduct(
                        Pageable.ofSize(size).withPage(page)
                )
        );
    }

    @GetMapping("/home/product/category/{id}")
    public ResponseObject<List<SimpleProductResponse>> findByCategoryId(@PathVariable Long id) {
        List<SimpleProductResponse> categories = productFacade.findByCategoryId(id);
        return ResponseHandler.response(categories);

    }

    @GetMapping("/home/product/parentid/{id}")
    public ResponseObject<List<SimpleProductResponse>> findByCategoryParentId(@PathVariable Long id) {
        List<SimpleProductResponse> categories = productFacade.findByCategoryParentId(id);
        return ResponseHandler.response(categories);

    }
}

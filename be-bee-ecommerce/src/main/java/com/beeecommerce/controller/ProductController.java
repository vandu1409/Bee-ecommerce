package com.beeecommerce.controller;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.ProductFacade;
import com.beeecommerce.model.dto.ProductDto;
import com.beeecommerce.model.request.ProductUpdateRequest;
import com.beeecommerce.model.response.ProductDetailResponse;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.ProductPath.PRODUCT_DETAIL;
import static com.beeecommerce.constance.ApiPaths.ProductPath.PRODUCT_PREFIX;

@RestController
@RequiredArgsConstructor
@MultipartConfig
@RequestMapping(PRODUCT_PREFIX)
public class ProductController {

    private final ProductFacade productFacade;

    private final ObjectMapper objectMapper;

    @GetMapping
    @Operation(summary = "Get all product management")
    public ResponseObject<List<SimpleProductResponse>> getAllOwnerProduct(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SimpleProductResponse> products = productFacade.getAllOwnerProduct(pageable);
        return ResponseHandler.response(products);
    }

    @PostMapping()
    public ResponseObject<String> create(
            @RequestPart List<MultipartFile> images,
            @RequestParam String data) throws ApplicationException, JsonProcessingException {

        ProductDto productDto = objectMapper.readValue(data, ProductDto.class);

        productDto.setFiles(images);

        productFacade.create(productDto);

        return ResponseHandler.response("Product created successfully");
    }

    @GetMapping(PRODUCT_DETAIL)
    public ResponseObject<List<ProductDetailResponse>> getProductDetail(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetailResponse> productDetailResponse = productFacade.getProductsDetail(pageable);
        return ResponseHandler.response(productDetailResponse);
    }

    @PutMapping("/{id}")
    public ResponseObject<String> update(@PathVariable("id") Long id, @RequestBody ProductDto productDto) throws ApplicationException {
        System.out.println(id);
        productFacade.updateProduct(id, productDto);
        return ResponseHandler.response("Product update successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseObject<String> delete(@PathVariable("id") Long id) throws ParamInvalidException {
        productFacade.deleteProductById(id);
        return ResponseHandler.response("Delete successfully");
    }

    @GetMapping("/{id}")
    public ResponseObject<ProductDetailResponse> findById(@PathVariable("id") Long id) throws ParamInvalidException {
        ProductDetailResponse optionalVoucher = productFacade.findById(id);
        return ResponseHandler.response(optionalVoucher);
    }

    @PutMapping("hidden/{id}")
    public ResponseObject<?> updateProductHidden(@PathVariable("id") Long id) throws ParamInvalidException {

        productFacade.updateProductHidden(id);

        return ResponseHandler.message("Update product successfully");
    }

    @PutMapping("update-classify/{id}")
    public ResponseObject<?> updateClassifyPriceAndInventory(@PathVariable("id") Long id, @RequestBody ProductUpdateRequest productUpdateRequest) throws ApplicationException {
        productFacade.updateClassifyPriceAndInventory(id, productUpdateRequest);
        return ResponseHandler.message("Update product successfully");
    }

    @GetMapping("find-similar/{id}")
    public ResponseObject<?> findSimilarProduct(@PathVariable("id") Long id,
                                                @RequestParam(defaultValue = "0") Integer page,
                                                @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SimpleProductResponse> pagesSimpleProductResponses = productFacade.findSimilarProduct(id, pageable);

        return ResponseHandler.response(pagesSimpleProductResponses);
    }


    @GetMapping("find-vendors/{id}")
    public ResponseObject<?> findVendorByProduct(@PathVariable Long id) throws ParamInvalidException {
        return ResponseHandler.response(productFacade.findVendorByProduct(id));
    }
    @GetMapping("edit/{id}")
    public ResponseObject<?> edit(@PathVariable Long id) throws ParamInvalidException {
        return ResponseHandler.response(productFacade.edit(id));
    }

    @GetMapping("vendor/{id}")
    public ResponseObject<?> findAllByVendor(@PathVariable Long id,  @RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "10") Integer size) throws ParamInvalidException {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseHandler.response(productFacade.findAllByVendors(id,pageable));
    }

}

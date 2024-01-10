package com.beeecommerce.controller;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.facade.ProductTagFacade;
import com.beeecommerce.model.dto.ProductTagDto;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.beeecommerce.constance.ApiPaths.ProductTagPath.PRODUCT_TAG_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(PRODUCT_TAG_PREFIX)
public class ProductTagController {

    private final ProductTagFacade productTagFacade;

    @GetMapping
    public ResponseObject<List<ProductTagDto>> getAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductTagDto> productTagDtos = productTagFacade.getAll(pageable);
        return ResponseHandler.response(productTagDtos);
    }

    @GetMapping("/{id}")
    public ResponseObject<ProductTagDto> findById(@PathVariable("id") Long id) throws ParamInvalidException {
        ProductTagDto optionalVoucher = productTagFacade.findById(id);
        return ResponseHandler.response(optionalVoucher);
    }

    @DeleteMapping("/{id}")
    public ResponseObject<String> delete(@PathVariable("id") Long id) throws ParamInvalidException {
        productTagFacade.deleteProductTagById(id);
        return ResponseHandler.response("Xóa Thành Công");
    }

//    @PostMapping
//    public ResponseObject<String> create(@RequestBody ProductTagDto productTagDto) throws ParamInvalidException  {
//        System.out.println(productTagDto.getHashtagDto().getId() + "hâsta");
//        System.out.println(productTagDto.getProductId() + "product");
//        productTagFacade.create(productTagDto);
//        return ResponseHandler.response("productTag created successfully");
//    }
//
//    @PutMapping("/{id}")
//    public ResponseObject<String> update(
//            @PathVariable("id") Long  id,
//            @RequestBody ProductTagDto productTagDto) throws ParamInvalidException  {
//        productTagFacade.updateProductTag(id, productTagDto);
//        return ResponseHandler.response("productTag update successfully");
//    }


}
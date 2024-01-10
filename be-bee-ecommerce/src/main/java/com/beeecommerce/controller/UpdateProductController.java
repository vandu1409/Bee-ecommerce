package com.beeecommerce.controller;

import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.ProductFacade;
import com.beeecommerce.model.request.UpdateProductRequest;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.beeecommerce.constance.ApiPaths.ProductPath.PRODUCT_PREFIX;

@RestController
@RequiredArgsConstructor
@MultipartConfig
@RequestMapping(PRODUCT_PREFIX)
public class UpdateProductController {

    private final ProductFacade productFacade;

    @PostMapping("/update-classifies-name/{id}")
    public ResponseObject<?> updateClassifyName(
            @PathVariable("id") Long productId,
            @RequestBody UpdateProductRequest request
            ) throws ApplicationException {

        request.setProductId(productId);

        productFacade.updateClassifyName(request);

        return ResponseHandler.message("Cập nhật thành công tên phân loại");
    }

    @PutMapping("update-product/{id}")
    public ResponseObject<?> updateProduct(@PathVariable Long id,@RequestBody UpdateProductRequest request){
        request.setProductId(id);

        productFacade.updateProduct(request);

        return ResponseHandler.response("Cập nhật sản phẩm thành công!");
    }


}

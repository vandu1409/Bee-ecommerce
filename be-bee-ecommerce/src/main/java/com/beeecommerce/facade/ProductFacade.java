package com.beeecommerce.facade;

import com.beeecommerce.constance.Role;
import com.beeecommerce.entity.Account;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.ProductDto;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.request.ProductUpdateRequest;
import com.beeecommerce.model.request.UpdateProductRequest;
import com.beeecommerce.model.response.ProductDetailResponse;
import com.beeecommerce.model.response.SimpleProductResponse;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.ProductPersonalService;
import com.beeecommerce.service.ProductService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFacade {
    private final ProductService productService;

    private final AccountService accountService;

    private final ProductPersonalService productPersonalService;

    public Page<SimpleProductResponse> getAllOwnerProduct(Pageable pageable) {

        Account account = accountService.getAuthenticatedAccount();

        if (account.getRole() == Role.ADMIN) {
            return productService.getAllProduct(pageable);
        } else {
            return productService.findAllByVendor(pageable);
        }

    }

    public void create(ProductDto productDto) throws ApplicationException {

//        CheckNullUtil.checkNullParam(
//                productDto.getBrandId(),
//                productDto.getVendorId(),
//                productDto.getCategoryId(),
//                productDto.getPrice(),
//                productDto.getName()
//        );

        productService.createProduct(productDto);

    }

    public void updateProduct(Long id, ProductDto productDto) throws ApplicationException {
        ObjectHelper.checkNullParam(
                id,
//                productDto.getBrandId(),
                productDto.getVendorId()
//                productDto.getCategoryId(),
//                productDto.getPrice(),
//                productDto.getName()
        );


        productDto.setId(id);
        productService.update(id, productDto);

    }

    public ProductDetailResponse findById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);
        return productService.getProductById(id);

    }

    public void deleteProductById(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi xóa ", e);
        }
    }

    public void updateProductHidden(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        productService.updateProductHidden(id);
    }

    public void updateClassifyPriceAndInventory(Long id, ProductUpdateRequest request) throws ApplicationException {
        ObjectHelper.checkNullParam(id);

        productService.updateClassifyPriceAndInventory(id, request);
    }


    public Page<ProductDetailResponse> getProductsDetail(Pageable pageable) {
        return productService.getAllProductDetail(pageable);
    }

    public Page<SimpleProductResponse> suggestProduct(Pageable pageable) {
        return productPersonalService
                .suggestProduct(pageable);


    }

    public Page<SimpleProductResponse> findSimilarProduct(Long id,Pageable pageable){
        return productPersonalService.findSimilarProduct(id,pageable);
    }

    public List<SimpleProductResponse> findByCategoryParentId(long parentId) {
        return productService.findByCategoryParentId(parentId);
    }

    public List<SimpleProductResponse> findByCategoryId(long parentId) {
        return productService.findByCategoryId(parentId);
    }


    public VendorDto findVendorByProduct(Long id) throws ParamInvalidException {

        ObjectHelper.checkNullParam(id);
        return productService.findByProduct(id);
}
    public ProductDto edit(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);
        return productService.edit(id);

    }


    public void updateClassifyName(UpdateProductRequest request) throws ParamInvalidException {
        ObjectHelper.checkNullParam(
                "Không được trống trường classifyName, classifyGroupName",
                request.getClassifyName(),
                request.getClassifyGroupName()
        );

         productService.updateClassifyName(request);

    }

    public void updateProduct(UpdateProductRequest request){
        productService.updateProduct(request);
    }

    public Page<SimpleProductResponse> findAllByVendors(Long id,Pageable pageable) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        return productService.findAllByVendor(id,pageable);
    }
}

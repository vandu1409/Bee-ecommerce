package com.beeecommerce.service;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.ProductDto;
import com.beeecommerce.model.dto.VendorDto;
import com.beeecommerce.model.request.ProductUpdateRequest;
import com.beeecommerce.model.request.UpdateProductRequest;
import com.beeecommerce.model.response.ProductDetailResponse;
import com.beeecommerce.model.response.SimpleProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<SimpleProductResponse> getAllProduct(Pageable pageable);

    void createProduct(ProductDto productDto) throws ApplicationException;

    ProductDetailResponse getProductById(Long id);

    ProductDto edit(Long id);

    void deleteProduct(Long id);

    void update(Long id, ProductDto productDto) throws ApplicationException;

    void updateProductHidden(Long productId);

    //    @Override
    void updateClassifyPriceAndInventory(Long productId, ProductUpdateRequest request) throws ApplicationException;

    Page<SimpleProductResponse> findAllByVendor(Long userId, Pageable pageable);

    Page<SimpleProductResponse> findAllByVendor(Pageable pageable) throws AuthenticateException;

    Page<ProductDetailResponse> getAllProductDetail(Pageable pageable);

    VendorDto findByProduct(Long id);

    void updateClassifyName(UpdateProductRequest request);

    void updateProduct(UpdateProductRequest updateProductRequest);

    List<SimpleProductResponse> findByCategoryId(long parentId);

    List<SimpleProductResponse> findByCategoryParentId(long parentId);
}

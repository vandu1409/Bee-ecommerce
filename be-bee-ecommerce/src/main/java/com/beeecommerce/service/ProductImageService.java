package com.beeecommerce.service;

import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.ProductImage;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import org.springframework.web.multipart.MultipartFile;

public interface ProductImageService {
    ProductImage addToProductImage(MultipartFile file, Product product) throws ApplicationException;

    void delete(Long id) throws EntityNotFoundException;
}

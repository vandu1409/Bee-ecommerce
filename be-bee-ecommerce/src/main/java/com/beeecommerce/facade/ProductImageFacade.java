package com.beeecommerce.facade;


import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.ProductImage;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.service.ProductImageService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductImageFacade {

    private final ProductImageService productImageService;

    public ProductImage addToProductImage(MultipartFile file, Product product) throws ApplicationException {
        return productImageService.addToProductImage(file, product);
    }

    public void delete(Long id) throws ApplicationException {
        ObjectHelper.checkNullParam(id);

        productImageService.delete(id);
    }


}

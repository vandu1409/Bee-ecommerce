package com.beeecommerce.service.impl;


import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.ProductImage;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.repository.ProductImageRepository;
import com.beeecommerce.service.AmazonClientService;
import com.beeecommerce.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {


    private final ProductImageRepository productImageRepository;

    private final AmazonClientService amazonClientService;

    @Override
    public ProductImage addToProductImage(MultipartFile file, Product product) throws ApplicationException {

        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);

        String fileUrl = amazonClientService.uploadFile(file, TypeUpLoad.IMAGE);

        productImage.setUrl(fileUrl);

        return productImageRepository.save(productImage);

    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        ProductImage productImage = productImageRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy ảnh sản phẩm phù hợp")
        );

        amazonClientService.deleteFileFromS3Bucket(productImage.getUrl());

        productImageRepository.delete(productImage);

    }


}



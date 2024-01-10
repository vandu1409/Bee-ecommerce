package com.beeecommerce.repository;

import com.beeecommerce.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query(value = "select clsg.image AS img_url from classtify_group clsg\n" +
            "UNION ALL\n" +
            "select fbImg.url AS img_url from feedback_image fbImg \n" +
            "UNION ALL\n" +
            "select productImg.url AS img_url from product_image productImg\n" +
            "UNION ALL\n" +
            "select product.poster AS img_url from product   \n" +
            "UNION ALL\n" +
            "select promoImg.url AS img_url from promotion_image promoImg \n", nativeQuery = true)
    List<Object> findAllImageUrls();
}
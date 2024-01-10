package com.beeecommerce.repository;

import com.beeecommerce.entity.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProductRepository extends JpaRepository<PromotionProduct, Long> {
}
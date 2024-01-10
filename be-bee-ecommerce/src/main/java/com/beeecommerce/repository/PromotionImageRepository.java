package com.beeecommerce.repository;

import com.beeecommerce.entity.PromotionImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionImageRepository extends JpaRepository<PromotionImage, Long> {
}
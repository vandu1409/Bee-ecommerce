package com.beeecommerce.repository;

import com.beeecommerce.entity.PromotionVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionVoucherRepository extends JpaRepository<PromotionVoucher, Long> {
}
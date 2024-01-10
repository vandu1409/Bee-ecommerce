package com.beeecommerce.repository;

import com.beeecommerce.entity.VoucherApplied;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherAppliedRepository extends JpaRepository<VoucherApplied, Long> {
}
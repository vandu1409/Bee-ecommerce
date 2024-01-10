package com.beeecommerce.repository;

import com.beeecommerce.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.vendors.id = ?1")
    Long getProductCount(Long id);
}
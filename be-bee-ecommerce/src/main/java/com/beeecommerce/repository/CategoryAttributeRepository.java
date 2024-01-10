package com.beeecommerce.repository;

import com.beeecommerce.entity.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, Long> {
}
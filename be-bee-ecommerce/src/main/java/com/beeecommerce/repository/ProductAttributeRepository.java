package com.beeecommerce.repository;

import com.beeecommerce.entity.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

    @Query("select p from ProductAttribute p where p.product.id = :id and p.attributes.name = :name ")
    Optional<ProductAttribute> findByProductAndAttributes(Long id, String name);

    @Query("select p from ProductAttribute p where p.product.id = :id ")
    List<ProductAttribute> findByProduct(Long id);

}
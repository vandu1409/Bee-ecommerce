package com.beeecommerce.repository;

import com.beeecommerce.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {

    @Query("select p from ProductTag p where p.product.id = :id and p.tag.name = :name")
    Optional<ProductTag> findByProductAndTag(Long id, String name);

    @Query("select p from ProductTag p where p.product.id = :id")
    List<ProductTag> findByProduct(Long id);

}
package com.beeecommerce.repository;

import com.beeecommerce.entity.Cart;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c " +
            "WHERE c.classify.id = ?1 AND c.user.id = ?2 ")
    Cart findByClassifyIdAndUserId(Long classifyId, Long userId);

    @Query("SELECT count(c) FROM Cart c where c.user.id = :id")
    Long countCartsByUserId(@Param("id") Long userId);

    @Query("SELECT c FROM Cart c " +
            "WHERE  c.user.id = ?1 " +
            "ORDER BY c.id, c.user.id ")
    Page<Cart> getUserCarts(Long userId, Pageable pageable);


}
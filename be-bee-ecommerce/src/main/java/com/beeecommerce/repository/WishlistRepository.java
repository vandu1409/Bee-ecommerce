package com.beeecommerce.repository;

import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    @Query("SELECT w FROM Wishlist w WHERE w.user.id = ?1 AND w.product.id = ?2")
    Wishlist findByUserIdAndProductId(Long userId, Long productId);


    @Query("select w from Wishlist w where w.user.id = ?1")
    Page<Wishlist> findByUser(Long id, Pageable pageable);
}
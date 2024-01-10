package com.beeecommerce.repository;

import com.beeecommerce.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.orderDetails.classify.group.product.id = ?1")
    public Page<Feedback> getByProduct(Long idProd, Pageable pageable);

}
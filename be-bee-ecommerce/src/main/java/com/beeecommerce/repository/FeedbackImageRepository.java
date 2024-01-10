package com.beeecommerce.repository;

import com.beeecommerce.entity.FeedbackImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackImageRepository extends JpaRepository<FeedbackImage, Long> {
}
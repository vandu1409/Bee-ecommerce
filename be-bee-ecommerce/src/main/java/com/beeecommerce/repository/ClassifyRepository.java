package com.beeecommerce.repository;

import com.beeecommerce.entity.Classify;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassifyRepository extends JpaRepository<Classify, Long> {

}
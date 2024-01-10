package com.beeecommerce.repository;

import com.beeecommerce.entity.ClassifyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasstifyGroupRepository extends JpaRepository<ClassifyGroup, Long> {
}
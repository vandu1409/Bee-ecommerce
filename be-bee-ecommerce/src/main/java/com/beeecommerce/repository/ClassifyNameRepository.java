package com.beeecommerce.repository;

import com.beeecommerce.entity.ClassifyName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClassifyNameRepository extends JpaRepository<ClassifyName, Long> {

    Optional<ClassifyName> findByName(String name);


}

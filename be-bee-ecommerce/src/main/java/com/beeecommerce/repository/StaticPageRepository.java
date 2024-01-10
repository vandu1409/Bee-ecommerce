package com.beeecommerce.repository;

import com.beeecommerce.entity.StaticPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StaticPageRepository extends JpaRepository<StaticPage, Long> {

    @Query("select s from StaticPage s where s.path = ?1")
    Optional<StaticPage> findByPath(String path);
}

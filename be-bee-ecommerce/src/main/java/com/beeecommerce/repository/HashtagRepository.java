package com.beeecommerce.repository;

import com.beeecommerce.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    @Query("SELECT h FROM Hashtag h WHERE h.name LIKE CONCAT('%', ?1 ,'%')")
    Page<Hashtag> findHashtagsByName(Pageable pageable, String name);

    Optional<Hashtag> findByName(String name);

}
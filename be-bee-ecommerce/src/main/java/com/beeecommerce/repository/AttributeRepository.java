package com.beeecommerce.repository;

import com.beeecommerce.entity.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Modifying
    @Query("UPDATE Attribute a SET a.name = :name WHERE a.id = :id")
    void updateAttribute(String name, Long id);

    @Query(""" 
            SELECT c.attributes
             FROM CategoryAttribute c
             WHERE c.category.id IN :categoryId
               AND c.required = true
            """)
    List<Attribute> getRequireAttribute(List<Long> categoryId);

    @Query("SELECT a FROM Attribute a WHERE a.name = :name ORDER BY a.id ASC")
    Optional<Attribute> findFirstByName(@Param("name") String name);



    List<Attribute> getAllByNameContaining(String keyword);
}
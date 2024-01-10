package com.beeecommerce.repository;

import com.beeecommerce.entity.Product;
import com.beeecommerce.entity.Vendor;
import com.beeecommerce.model.response.SimpleProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.vendors.id = :id")
    Page<Product> findAllByUser(Long id, Pageable pageable);

    @Query(value = "select * from Product order by newid()", nativeQuery = true)
    Page<Product> randomProduct(Pageable pageable);

    @Query(value = "SELECT * FROM product\n" +
            "ORDER BY NEWID() OFFSET 0 ROWS FETCH FIRST ?1 ROWS ONLY", nativeQuery = true)
    List<Product> randomProductByTop(int top);



    @Query("select p.vendors from Product p where p.id =:id")
    Optional<Vendor> findByProduct(Long id);

    @Query("SELECT p FROM Category c JOIN c.products p WHERE c.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);


    List<Product> findByCategoryParentId(Long parentId);
}
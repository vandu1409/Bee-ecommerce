package com.beeecommerce.repository;

import com.beeecommerce.entity.Address;
import com.beeecommerce.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("select a from Address a where a.user.id=:id")
    Page<Address> findByUser(Long id, Pageable pageable);
}
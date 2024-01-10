package com.beeecommerce.repository;

import com.beeecommerce.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.receiver.id = ?1 AND t.status = 'PENDING' AND t.type = 'DEPOSIT'")
    Optional<Transaction> getDepositPending(Long id);

    Optional<Transaction> findByOrderId(Long id);
}
package com.beeecommerce.repository;

import com.beeecommerce.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a " +
            "where  a.username = ?1 or a.phoneNumber = ?1 or a.email = ?1")
    Optional<Account> loadAccount(String keyword);

    @Query("select a from Account a " +
            "where  a.username = ?1 or a.phoneNumber = ?1 or a.email = ?1 and a.password = ?2")
    Optional<Account> checkLoginCredentials(String keyword, String password);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhoneNumber(String phone);
}
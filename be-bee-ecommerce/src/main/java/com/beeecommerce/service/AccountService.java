package com.beeecommerce.service;

import com.beeecommerce.entity.Account;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.AccountDto;
import com.beeecommerce.model.request.AccountRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


public interface AccountService extends UserDetailsService {

    Account save(AccountDto accountDto);

    Optional<Account> findById(Long id);

    Optional<Account> findByPhoneNumber(String phone);

    Optional<Account> findByUsername(String username);

    Optional<Account> checkLoginCredentials(String keyword, String password);

    Optional<Account> loadAccount(String keyword);


    /**
     * This method support find Account by [username, email, number phone]
     *
     * @param username
     * @return Account
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Account getAuthenticatedAccount() throws AuthenticateException;

    void updateAccountInfo(AccountRequest accountRequest, MultipartFile multipartFile) throws ApplicationException;

}

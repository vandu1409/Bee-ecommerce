package com.beeecommerce.service.impl;

import com.beeecommerce.constance.TypeUpLoad;
import com.beeecommerce.entity.Account;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.AccountDto;
import com.beeecommerce.model.request.AccountRequest;
import com.beeecommerce.repository.AccountRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.AmazonClientService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final AmazonClientService amazonClientService;

    /**
     * This method support find Account by [username, email, number phone]
     *
     * @param username
     * @return Account
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository
                .loadAccount(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Not found account with " + username)
                );
    }

    @Override
    public Account getAuthenticatedAccount() throws AuthenticateException {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth.getPrincipal() instanceof Account account) {
            return account;
        }
        throw new AuthenticateException("Require login to request");

    }

    @Override
    public void updateAccountInfo(AccountRequest accountReq, MultipartFile multipartFile) throws ApplicationException {

        Account account = getAuthenticatedAccount();

        ObjectHelper.setIfNotNull(accountReq.getFullname(), account::setFullname);
        ObjectHelper.setIfNotNull(accountReq.getPhoneNumber(), account::setPhoneNumber);

        if (multipartFile != null) {
            String oldAvatar = account.getAvatar();
            String url = amazonClientService.uploadFile(multipartFile, TypeUpLoad.IMAGE);
            if (oldAvatar != null) {
                amazonClientService.deleteFileFromS3Bucket(oldAvatar);
            }
            account.setAvatar(url);
        }

        accountRepository.save(account);
    }


    @Override
    public Account save(AccountDto accountDto) {
        return null;
    }


    @Override
    public Optional<Account> loadAccount(String keyword) {
        return accountRepository.loadAccount(keyword);
    }

    @Override
    public Optional<Account> checkLoginCredentials(String keyword, String password) {
        return accountRepository.checkLoginCredentials(keyword, password);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Optional<Account> findByPhoneNumber(String phone) {
        return accountRepository.findByPhoneNumber(phone);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

}

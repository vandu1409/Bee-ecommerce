package com.beeecommerce.service;

import com.beeecommerce.entity.Account;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.auth.RefreshTokenNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.request.AuthenticationRequest;
import com.beeecommerce.model.request.RegisterRequest;
import com.beeecommerce.model.response.AuthenticateResponse;

import java.util.Optional;

public interface AuthenticateService {

    void register(RegisterRequest request) throws ApplicationException;

    AuthenticateResponse login(AuthenticationRequest authenticationRequest) throws AuthenticateException;

    void requestResetPassword(String username) throws ApplicationException;

    void resetPassword(AuthenticationRequest authenticationRequest) throws Exception;

    void requestConfirmAccount(String username) throws ApplicationException;

    void confirmAccount(String token) throws AuthenticateException;

    Optional<Account> findByUsername(String username);


    AuthenticateResponse authentication() throws AuthenticateException;

    void logout() throws AuthenticateException;

    String refreshToken(String refreshToken) throws RefreshTokenNotFoundException;
}

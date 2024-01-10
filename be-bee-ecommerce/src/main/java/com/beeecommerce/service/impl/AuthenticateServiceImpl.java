package com.beeecommerce.service.impl;

import com.beeecommerce.constance.Role;
import com.beeecommerce.entity.Account;
import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.auth.RefreshTokenNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.mapper.AuthenticateMapper;
import com.beeecommerce.model.request.AuthenticationRequest;
import com.beeecommerce.model.request.RegisterRequest;
import com.beeecommerce.model.response.AuthenticateResponse;
import com.beeecommerce.repository.AccountRepository;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.AuthenticateService;
import com.beeecommerce.service.JwtService;
import com.beeecommerce.service.SendEmailConfirm;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticateServiceImpl implements AuthenticateService {

    private final JwtService jwtService;

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    private final AccountService accountService;

    private final SendEmailConfirm sendEmailConfirm;
    @Value("${app.auth.resetPasswordLink}")
    private  String resetPasswordLink;

    @Value("${app.auth.confirmAccountLink}")
    private  String confirmAccountLink;

    private final AuthenticationManager authenticationManager;

    private final AuthenticateMapper authenticateMapper;

    private final RedisTemplate<String, String> redisTemplate;


    @Transactional
    @Override
    public void register(RegisterRequest registerRequest) throws ApplicationException {

        Account account = Account
                .builder()
                .username(registerRequest.getUsername())
                .fullname(registerRequest.getFullname())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();

        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        account.setIsActive(false);
        account.setCreateAt(LocalDate.now());
        account.setRole(Role.CUSTOMER);

        accountRepository.save(account);

        sendEmailConfirm.sendMailConfirmAccount(account);

    }

    @Override
    public AuthenticateResponse login(AuthenticationRequest authenticationRequest) throws AuthenticateException {

        Account account = accountRepository
                .findByUsername(authenticationRequest.getUsername())
                .orElseThrow(
                        () -> new AuthenticateException("Không tìm thấy tài khoản có tên đăng nhập : " + authenticationRequest.getUsername())
                );

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new AuthenticateException("Login info not match");
        }

        return authenticateMapper.apply(account);

    }

    @Override
    public void requestResetPassword(String username) throws ApplicationException {
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new AuthenticateException("Không tìm thấy tài khoản có tên đăng nhập : " + username)
                );


        String jwtToken = jwtService.generateResetPasswordToken(account);

        String link = resetPasswordLink + jwtToken;

        sendEmailConfirm.sendMailResetPasswordConfirm(link, account);
    }

    @Override
    public void resetPassword(AuthenticationRequest authenticationRequest) throws Exception {

        Account account = jwtService.isValidateResetPasswordToken(authenticationRequest.getToken());

        account.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));

        accountRepository.save(account);

    }

    @Override
    public void requestConfirmAccount(String username) throws ApplicationException {
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new AuthenticateException("Không tìm thấy tài khoản có tên đăng nhập : " + username)
                );

        sendEmailConfirm.sendMailConfirmAccount(account);


    }

    @Override
    public void confirmAccount(String token) throws AuthenticateException {

        String username = jwtService.extractUsername(token.trim());

        Account account = accountRepository
                .loadAccount(username)
                .orElseThrow(
                        () -> new AuthenticateException("Không tìm thấy tài khoản!Vui lòng kiểm tra lại!")
                );

        account.setIsActive(true);

        accountRepository.save(account);
    }


    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public AuthenticateResponse authentication() throws AuthenticateException {

        Account account = accountService.getAuthenticatedAccount();

        return authenticateMapper.apply(account, false, false);
    }

    @Override
    public void logout() throws AuthenticateException {

        Account loggedAccount = accountService.getAuthenticatedAccount();

        //TODO remove refresh token from redis
        redisTemplate.opsForValue().getAndDelete(loggedAccount.getUsername());

    }

    @Override
    public String refreshToken(String refreshToken) throws RefreshTokenNotFoundException {

        Account account = jwtService.extractAccount(refreshToken);

        String redisToken = redisTemplate.opsForValue().get("refreshToken::" + account.getUsername());

        if (redisToken == null || !redisToken.equals(refreshToken)) {
            throw new RefreshTokenNotFoundException("Refresh token is invalid!");
        }


        String accessToken = jwtService.generateAccessToken(account);

        return accessToken;
    }

}

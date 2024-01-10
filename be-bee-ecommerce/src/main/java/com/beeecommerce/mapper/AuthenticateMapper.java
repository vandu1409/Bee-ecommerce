package com.beeecommerce.mapper;

import com.beeecommerce.entity.Account;
import com.beeecommerce.model.response.AuthenticateResponse;
import com.beeecommerce.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AuthenticateMapper implements Function<Account, AuthenticateResponse> {

    private final JwtService jwtService;

    private final VendorMapper vendorMapper;

    @Override
    public AuthenticateResponse apply(Account account) {
        return apply(account, true, true);
    }

    @Transactional
    public AuthenticateResponse apply(Account account, boolean getAccessToken, boolean getRefreshToken) {

        return AuthenticateResponse
                .builder()
                .username(account.getUsername())
                .email(account.getEmail())
                .fullname(account.getFullname())
                .phoneNumber(account.getPhoneNumber())
                .updateAt(account.getUpdateAt())
                .isActive(account.getIsActive())
                .lastLoginTime(account.getLastLoginTime())
                .avatar(account.getAvatar())
                .role(account.getRole())
                .balance(account.getBalance())
                .vendor(
                        account.getVendors() != null
                                ? vendorMapper.apply(account.getVendor())
                                : null
                )
                .refreshToken(
                        getRefreshToken
                                ? jwtService.generateRefreshToken(account)
                                : null
                )
                .accessToken(
                        getAccessToken
                                ? jwtService.generateAccessToken(account)
                                : null
                )
                .build();

    }
}

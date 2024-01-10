package com.beeecommerce.mapper;

import com.beeecommerce.entity.Account;
import com.beeecommerce.model.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AccountMapper implements Function<Account, AccountDto> {

    private final VendorMapper vendorMapper;

    @Override
    public AccountDto apply(Account account) {
        return AccountDto
                .builder()
                .email(account.getEmail())
                .username(account.getUsername())
                .fullname(account.getFullname())
                .phoneNumber(account.getPhoneNumber())
                .updateAt(account.getUpdateAt())
                .isActive(account.getIsActive())
                .lastLoginTime(account.getLastLoginTime())
                .vendor(
                        account.getVendor() != null
                                ? vendorMapper.apply(account.getVendor())
                                : null
                )
                .build();
    }
}

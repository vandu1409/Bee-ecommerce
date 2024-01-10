package com.beeecommerce.model.response;

import com.beeecommerce.constance.Role;
import com.beeecommerce.model.dto.AccountDto;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
public class AuthenticateResponse extends AccountDto implements Serializable {

    private Role role;

    private Long balance = 0L;

    private String accessToken;

    private String refreshToken;


}

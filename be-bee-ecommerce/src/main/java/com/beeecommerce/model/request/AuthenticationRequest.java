package com.beeecommerce.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    private String token;

    private String refreshToken;

    private String username;

    private String password;

    private String confirmPassword;
}

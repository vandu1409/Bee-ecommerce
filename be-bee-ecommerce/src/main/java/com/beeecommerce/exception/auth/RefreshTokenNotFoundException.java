package com.beeecommerce.exception.auth;

import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends AuthenticateException {

    public RefreshTokenNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
}

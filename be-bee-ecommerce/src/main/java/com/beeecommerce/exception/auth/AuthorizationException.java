package com.beeecommerce.exception.auth;

import com.beeecommerce.exception.core.ApplicationException;
import org.springframework.http.HttpStatus;

public class AuthorizationException extends ApplicationException {
    public AuthorizationException(String message) {
        super(message);
        this.status = HttpStatus.FORBIDDEN;
    }
}

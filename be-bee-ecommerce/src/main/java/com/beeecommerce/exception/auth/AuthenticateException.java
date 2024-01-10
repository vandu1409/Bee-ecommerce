package com.beeecommerce.exception.auth;

import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.exception.core.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class AuthenticateException extends ApplicationRuntimeException {

    public AuthenticateException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }


}

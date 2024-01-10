package com.beeecommerce.exception.common;

import com.beeecommerce.exception.core.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class OAuth2AuthenticationProcessingException extends ApplicationRuntimeException {

    public OAuth2AuthenticationProcessingException(String message) {
        super(message);
        this.status = HttpStatus.UNAUTHORIZED;
    }
}

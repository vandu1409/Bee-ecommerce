package com.beeecommerce.exception.common;

import com.beeecommerce.exception.core.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends ApplicationRuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
    }

}

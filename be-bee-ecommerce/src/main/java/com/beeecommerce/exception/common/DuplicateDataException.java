package com.beeecommerce.exception.common;

import com.beeecommerce.exception.core.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicateDataException extends ApplicationException {
    public DuplicateDataException(String message) {
        super(message);
        super.status = HttpStatus.NOT_IMPLEMENTED;
    }
}

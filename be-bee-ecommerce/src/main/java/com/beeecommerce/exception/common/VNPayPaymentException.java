package com.beeecommerce.exception.common;

import com.beeecommerce.exception.core.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class VNPayPaymentException extends ApplicationRuntimeException {
    public VNPayPaymentException(String key) {
        super(key);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

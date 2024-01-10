package com.beeecommerce.exception.common;

import com.beeecommerce.exception.core.ApplicationException;

public class OutOfStockException extends ApplicationException {

    public OutOfStockException(String message) {
        super(message);
    }

}

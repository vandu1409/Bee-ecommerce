package com.beeecommerce.model.response.common;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ResponseObject<T> implements Serializable {

    T data;

    String message = "Not have message return";

    boolean result;

    int statusCode;

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    PaginationResponse pagination;

    public ResponseObject(T body, HttpStatus status) {
        this.result = status.is2xxSuccessful();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        statusCode = status.value();
        this.data = body;
    }

    public void setStatus(HttpStatus status) {
        this.statusCode = status.value();
        this.result = status.is2xxSuccessful();
    }

    public void setPagination(Page<?> page) {
        pagination = new PaginationResponse();
        pagination.setCurrentPage(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
    }

    public ResponseObject<T> status(HttpStatus status) {
        this.setStatus(status);
        return this;
    }


    public ResponseObject<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResponseObject<T> response(T data) {
        this.data = data;
        return this;
    }

    public ResponseObject<T> result(boolean result) {
        this.result = result;
        return this;
    }
}

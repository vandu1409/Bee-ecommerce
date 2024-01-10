package com.beeecommerce.model.response.common;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResponseHandler {

    /**
     * This method not support pagination. Default HttpStatus is [200 - OK]
     *
     * @param data
     * @param status
     * @param <T>
     * @return ResponseObject<T>
     */
    private static <T> ResponseObject<T> response(T data, HttpStatus status) {
        return new ResponseObject<>(data, status);
    }

    public static <T> ResponseObject<T> response(T data) {
        return response(data, HttpStatus.OK);
    }


    /**
     * This method support pagination. Default HttpStatus is [200 - OK]
     *
     * @param status
     * @param <T>
     * @return ResponseObject<T>
     */
    public static <T> ResponseObject<List<T>> response(Page<T> page, HttpStatus status) {
        ResponseObject<List<T>> response = new ResponseObject<>(page.getContent(), status);
        response.setPagination(page);
        return response;
    }


    public static <T> ResponseObject<List<T>> response(Page<T> data) {
        return response(data, HttpStatus.OK);
    }

    public static <T> ResponseObject<List<T>> response(List<T> data, Comparator<T> comparator) {
        List<T> sorted = new ArrayList<>(data);
        sorted.sort(comparator);
        return response(sorted, HttpStatus.OK);
    }

    public static ResponseObject<?> message(String message, HttpStatus status) {
        return new ResponseObject<>(null, status)
                .message(message);
    }

    public static ResponseObject<?> message(String message) {
        return message(message, HttpStatus.OK);
    }
}

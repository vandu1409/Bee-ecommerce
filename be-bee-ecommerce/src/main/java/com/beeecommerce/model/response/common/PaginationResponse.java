package com.beeecommerce.model.response.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaginationResponse implements Serializable {

    private long totalElements;

    private int totalPages;

    private int currentPage;

    private int pageSize;
}


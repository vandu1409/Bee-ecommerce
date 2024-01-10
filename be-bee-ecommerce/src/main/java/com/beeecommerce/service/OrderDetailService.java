package com.beeecommerce.service;

import com.beeecommerce.entity.Classify;
import com.beeecommerce.entity.Order;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.model.dto.OrderDetailDto;

import java.util.List;

public interface OrderDetailService {
    void insert(Long quantity, Double totalPrice, Order order, Classify classify);

    List<OrderDetailDto> getOrderDetailByOrderId(Long id) throws ParamInvalidException;
}

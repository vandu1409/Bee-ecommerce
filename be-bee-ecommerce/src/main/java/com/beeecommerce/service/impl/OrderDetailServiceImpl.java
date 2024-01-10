package com.beeecommerce.service.impl;

import com.beeecommerce.entity.Classify;
import com.beeecommerce.entity.Order;
import com.beeecommerce.entity.OrderDetail;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.mapper.OrderDetailMapper;
import com.beeecommerce.model.dto.OrderDetailDto;
import com.beeecommerce.repository.OrderDetailRepository;
import com.beeecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public void insert(Long quantity, Double totalPrice, Order order, Classify classify) {
//
//        OrderDetail orderDetail = new OrderDetail();
//
//        orderDetail.builder()
//                        .quantity(quantity)
//                        .totalPrice(totalPrice)
//                        .order(order)
//                        .classtify(classify)
//                        .build();
//
//        orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetailDto> getOrderDetailByOrderId(Long id) throws ParamInvalidException {

        List<OrderDetail> orderDetails = orderDetailRepository
                .findByOrderId(id);

        if (orderDetails.isEmpty()) {
            throw new ParamInvalidException("Order id is invalid");
        }

        return orderDetails
                .stream()
                .map(orderDetailMapper)
                .toList();
    }
}

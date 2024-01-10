package com.beeecommerce.mapper;

import com.beeecommerce.entity.OrderDetail;
import com.beeecommerce.entity.Product;
import com.beeecommerce.model.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderDetailMapper implements Function<OrderDetail, OrderDetailDto> {

    private final SimpleProductResponseMapper simpleProductResponseMapper;

    private final ClassifyMapper classifyMapper;

    @Override
    public OrderDetailDto apply(OrderDetail orderDetail) {
        boolean hasFeedback = orderDetail.hasFeedback();
        return OrderDetailDto.builder()
                .id(orderDetail.getId())
                .quantity(orderDetail.getQuantity())
                .totalPrice(orderDetail.getTotalPrice())
                .classifyId(orderDetail.getClassify().getId())
                .orderId(orderDetail.getOrder().getId())
                .classifyDto(classifyMapper.apply(orderDetail.getClassify()))
                .productName(orderDetail.getProduct().getName())
                .shopName(orderDetail.getProduct().getVendors().getName())

                .hasFeedback(hasFeedback)
                // .(simpleProductResponseMapper.apply(orderDetail.getProduct()))
                .build();
    }

}

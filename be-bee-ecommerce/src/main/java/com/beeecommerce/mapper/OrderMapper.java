package com.beeecommerce.mapper;

import com.beeecommerce.entity.Order;
import com.beeecommerce.entity.OrderDetail;
import com.beeecommerce.model.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class OrderMapper implements Function<Order, OrderDto> {

    private final OrderDetailMapper orderDetailMapper;

    private final AddressMapper addressMapper;

    @Override
    public OrderDto apply(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .discount(order.getDiscount())
                .amount(order.getAmount())
                .notes(order.getNotes())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .createAt(order.getCreateAt())
                .deliveryAt(order.getDeliveryAt())
                .status(order.getStatus())
                .addressId(order.getAddress().getId())
                .addressDto(addressMapper.apply(order.getAddress()))
                .userId(order.getUser().getId())
                .shippingFee(order.getShippingFee())
                .shopName(order.getVendor().getName() == null ?"":order.getVendor().getName())
                .shopId(order.getVendor()==null?0l:order.getVendor().getId())
                .voucherApplied(
                        order.getVouchersApplied()
                                .stream()
                                .map(listVoucher -> listVoucher.getVoucher().getId())
                                .toList()
                )
                .orderDetailId(
                        order.getOrderDetails()
                                .stream()
                                .map(OrderDetail::getId)
                                .toList()
                ).orderDetailsDto(order.getOrderDetails().stream().map(orderDetailMapper).toList())
                .build();
    }
}

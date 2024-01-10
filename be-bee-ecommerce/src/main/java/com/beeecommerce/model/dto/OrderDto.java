package com.beeecommerce.model.dto;

import com.beeecommerce.constance.OrderStatus;
import com.beeecommerce.constance.PaymentMethod;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class OrderDto implements Serializable {

    private Long id;

    private Long totalPrice;

    private Long amount;

    private Long discount;

    private String notes;

    private LocalDate createAt;

    private LocalDate deliveryAt;

    private String shopName;


    private PaymentMethod paymentMethod;

    private Long shippingFee;

    private String paymentStatus;

    private Long userId;

    private Long shopId;

    private OrderStatus status;

    private List<Long> voucherApplied;

    private Long addressId;

    private AddressDto addressDto;

    private List<Long> orderDetailId;

    private List<OrderDetailDto> orderDetailsDto;
}

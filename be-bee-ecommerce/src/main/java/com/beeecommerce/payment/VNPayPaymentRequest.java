package com.beeecommerce.payment;

import com.beeecommerce.entity.Vendor;

public interface VNPayPaymentRequest {
    Long getAmount();          // Trả về số tiền thanh toán không cần nhân 100

    default Long getFinalAmount() {
        return getAmount() * 100;
    }     // Trả về số tiền thanh toán đã nhân 100

    String getOrderInfo();

    String getOrderType();

    String getTxnRef();

    default boolean readyToPay() {
        return false;
    }


}

package com.beeecommerce.service;

import com.beeecommerce.exception.common.VNPayPaymentException;
import com.beeecommerce.payment.IPNResponse;
import com.beeecommerce.payment.VNPayPaymentRequest;
import com.beeecommerce.payment.VNPayUtils;

import java.util.Map;

public interface VNPayService {

    VNPayUtils createPaymentUtils(VNPayPaymentRequest request);

    default String createPaymentUrl(VNPayPaymentRequest request) {
        try {
            return createPaymentUtils(request).generateUrl();
        } catch (Exception e) {
            if (e instanceof VNPayPaymentException ex) {
                throw ex;
            } else {
                throw new VNPayPaymentException(e.getMessage());
            }
        }
    }

    ;

    String requestDeposit(Long amount);

    IPNResponse processPayment(Map<String, String> vnpayResponse);
}

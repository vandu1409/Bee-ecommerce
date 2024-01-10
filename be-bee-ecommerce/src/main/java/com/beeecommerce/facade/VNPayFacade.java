package com.beeecommerce.facade;

import com.beeecommerce.payment.IPNResponse;
import com.beeecommerce.payment.VNPayUtils;
import com.beeecommerce.service.VNPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VNPayFacade {

    public final VNPayService vnPayService;

    @Value("${app.payment.vnpay.secret-key}")
    private String secretKey;

    public String requestDeposit(Long amount) {
        return vnPayService.requestDeposit(amount);
    }

    public IPNResponse processPayment(Map<String, String> vnpayResponse) throws NoSuchAlgorithmException, InvalidKeyException {

        // Check response signature
        boolean result =  VNPayUtils.checksum(vnpayResponse, secretKey);
        if (!result) {
            return new IPNResponse("Invalid Checksum", "97");
        }

        return vnPayService.processPayment(vnpayResponse);
    }

    public Map<String,String> checkSum(Map<String,String> vnpayResponse) throws NoSuchAlgorithmException, InvalidKeyException {
        boolean result =  VNPayUtils.checksum(vnpayResponse, secretKey);

        vnpayResponse.put("result", String.valueOf(result));

        return vnpayResponse;
    }
}

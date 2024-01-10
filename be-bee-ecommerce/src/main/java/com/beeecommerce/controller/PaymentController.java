package com.beeecommerce.controller;

import com.beeecommerce.facade.VNPayFacade;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.beeecommerce.payment.IPNResponse;
import com.beeecommerce.payment.VNPayPaymentRequest;
import com.beeecommerce.service.VNPayService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;
import java.util.Enumeration;

import static com.beeecommerce.constance.ApiPaths.PaymentPath.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(PAYMENT_PREFIX)
public class PaymentController {

    private final VNPayService vnpayService;
    private final VNPayFacade vnpayFacade;

    @GetMapping()
    public ResponseObject<String> getPayment() {

        VNPayPaymentRequest request = new VNPayPaymentRequest() {

            @Override
            public Long getAmount() {
                return 10000L;
            }

            @Override
            public String getOrderInfo() {
                return "Test order info";
            }

            @Override
            public String getOrderType() {
                return "TEST";
            }

            @Override
            public String getTxnRef() {
                return UUID.randomUUID().toString();
            }

            @Override
            public boolean readyToPay() {
                return true;
            }
        };

        String url = vnpayService.createPaymentUrl(request);

        return ResponseHandler.response(url);
    }


    @PostMapping(REQUEST_DEPOSIT)
    @Operation(summary = "Request deposit")
    public ResponseObject<?> requestDeposit(@RequestParam Long amount) {
        String paymentUrl = vnpayFacade.requestDeposit(amount);
        return ResponseHandler.response(paymentUrl).message("Request deposit successfully");
    }

    @GetMapping(PROCESS)
    @CrossOrigin(origins = "*")
    @Operation(summary = "Process payment", description = "Xử lý thanh toán sau khi thanh toán xong")
    public IPNResponse processPayment(@RequestParam Map<String, String> vnpayResponse, HttpServletRequest request) {

        Enumeration<String> headers = request.getHeaders("X-Forwarded-For");


        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            System.out.println("Header : " + header);
        }

        try {
            return vnpayFacade.processPayment(vnpayResponse);
        } catch (Exception e) {
            return null;

        }
    }

    @PostMapping("checksum")
    public ResponseObject<?> checkSum(@RequestBody Map<String, String> vnpayResponse) throws NoSuchAlgorithmException, InvalidKeyException {
        return ResponseHandler.response(vnpayFacade.checkSum(vnpayResponse));
    }

}

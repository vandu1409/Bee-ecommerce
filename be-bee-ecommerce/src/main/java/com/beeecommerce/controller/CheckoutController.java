package com.beeecommerce.controller;

import com.beeecommerce.entity.Order;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.common.VoucherNotApplicableException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.CheckoutFacade;
import com.beeecommerce.model.request.CheckoutRequest;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.beeecommerce.constance.ApiPaths.CheckoutPath.CHECKOUT_PREFIX;

@RestController
@RequiredArgsConstructor
@RequestMapping(CHECKOUT_PREFIX)
public class CheckoutController {
    private final CheckoutFacade checkoutFacade;

    @PostMapping
    public ResponseObject<?> checkout(@RequestBody CheckoutRequest request) throws ApplicationException, VoucherNotApplicableException {


        return ResponseHandler.response( checkoutFacade.checkout(request));
    }

    @PostMapping("payment-vnpay/{id}")
    public ResponseObject<?> paymentVnpay(@PathVariable("id") Long id) throws ParamInvalidException {
        return ResponseHandler.response(checkoutFacade.paymentVnPay(id));
    }
}

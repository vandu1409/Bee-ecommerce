package com.beeecommerce.facade;

import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.common.VoucherNotApplicableException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.OrderDto;
import com.beeecommerce.model.request.CheckoutRequest;
import com.beeecommerce.service.OrderService;
import com.beeecommerce.service.SendEmailConfirm;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutFacade {

    private final OrderService orderService;

    private final SendEmailConfirm sendEmailConfirm;

    public OrderDto checkout(CheckoutRequest checkoutRequest) throws ApplicationException, VoucherNotApplicableException {

        OrderDto orderDto = orderService.insert(checkoutRequest);


        return orderDto;
//        sendEmailConfirm.sendMailOrderDetail(orderDto);
    }

    public String paymentVnPay(Long id) throws ParamInvalidException {
        ObjectHelper.checkNullParam(id);

        return orderService.paymentVnpay(id);
    }
}

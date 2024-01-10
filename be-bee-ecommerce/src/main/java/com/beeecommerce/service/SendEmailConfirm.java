package com.beeecommerce.service;

import com.beeecommerce.entity.Account;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.OrderDto;

public interface SendEmailConfirm {
    void sendMailOrderDetail(OrderDto orderDto) throws ApplicationException;

    void sendMailResetPasswordConfirm(String link, Account account) throws ApplicationException;

    void sendMailConfirmAccount(Account account) throws ApplicationException;
}

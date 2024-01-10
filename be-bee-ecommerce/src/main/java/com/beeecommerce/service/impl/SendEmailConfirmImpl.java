package com.beeecommerce.service.impl;

import com.beeecommerce.entity.Account;
import com.beeecommerce.entity.OrderDetail;
import com.beeecommerce.exception.common.EntityNotFoundException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.dto.OrderDto;
import com.beeecommerce.repository.OrderDetailRepository;
import com.beeecommerce.service.EmailService;
import com.beeecommerce.service.SendEmailConfirm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendEmailConfirmImpl implements SendEmailConfirm {

    private final TemplateEngine templateEngine;

    private final EmailService emailService;

    private final OrderDetailRepository orderDetailRepository;

    private final JwtServiceImpl jwtService;


    @Value("${app.auth.confirmAccountLink}")
    private String confirmAccountLink;

    @Value("${app.mail.subject.resetPassword}")
    private String subjectResetPassword;

    @Override
    public void sendMailOrderDetail(OrderDto orderDto) throws ApplicationException {
        List<Long> listIdOrderDetail = orderDto.getOrderDetailId();

        Double totalPriceProd = 0.0;
        for (Long id : listIdOrderDetail) {
            OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Không tìm thấy chi tiết đơn hàng!"));

            totalPriceProd += orderDetail.getTotalPrice();
        }

        Context context = new Context();
        context.setVariable("listIdOrderDetail", listIdOrderDetail);
        context.setVariable("orderDto", orderDto);
        context.setVariable("totalPriceProd", totalPriceProd);

        //tạo html
        String htmlContent = templateEngine.process("/orderConfirmation.html", context);

        String recipient = "vanvu19102003@gmail.com";
        emailService.sendSimpleMail("THÔNG TIN CHI TIẾT ĐƠN HÀNG", htmlContent, recipient);
    }

    @Override
    public void sendMailResetPasswordConfirm(String link, Account account) throws ApplicationException {


        String recipient = account.getEmail();
        String fullname = account.getFullname();

        Context context = new Context();
        context.setVariable("fullname", fullname);
        context.setVariable("link", link);

        //tạo html
        String htmlContent = templateEngine.process("/resetPassword.html", context);

        emailService.sendSimpleMail(subjectResetPassword, htmlContent, recipient);
    }

    @Override
    public void sendMailConfirmAccount(Account account) throws ApplicationException {

        Context context = new Context();

        String link = confirmAccountLink + jwtService.generateAccessToken(account);

        context.setVariable("link", link);

        //tạo html
        String htmlContent = templateEngine.process("/emailVerify.html", context);

        emailService.sendSimpleMail("CONFIRM YOUR ACCOUNT", htmlContent, account.getEmail());
    }
}

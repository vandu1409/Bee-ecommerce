package com.beeecommerce.service.impl;

import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Autowired
    private HttpServletRequest httpRequest;

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Override
    public void sendSimpleMail(String subject, String body, String recipient) throws ApplicationException {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true); // Set the HTML content

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new ApplicationException("Gửi mail không thành công!");
        }
    }
}

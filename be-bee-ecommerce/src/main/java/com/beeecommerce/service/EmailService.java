package com.beeecommerce.service;

import com.beeecommerce.exception.core.ApplicationException;

public interface EmailService {

    void sendSimpleMail(String subject, String body, String recipient) throws ApplicationException;


}

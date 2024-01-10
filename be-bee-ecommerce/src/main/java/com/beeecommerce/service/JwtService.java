package com.beeecommerce.service;

import com.beeecommerce.entity.Account;

public interface JwtService {


    String extractUsername(String token);

    Account extractAccount(String token);

    String generateAccessToken(Account account);

    String generateRefreshToken(Account account);

    String generateResetPasswordToken(Account account);

    //    public String generateRegisterToken(Account account){
    //        return  generateToken(convertToMap(account),SECRET_KEY,ACCESS_TOKEN_EXPIRATION_TIME);
    //    }
    boolean isValidToken(String token);

    Account isValidateResetPasswordToken(String token) throws Exception;
}

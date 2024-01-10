package com.beeecommerce.facade;

import com.beeecommerce.exception.auth.AuthenticateException;
import com.beeecommerce.exception.auth.RefreshTokenNotFoundException;
import com.beeecommerce.exception.common.ParamInvalidException;
import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.model.request.AccountRequest;
import com.beeecommerce.model.request.AuthenticationRequest;
import com.beeecommerce.model.request.RegisterRequest;
import com.beeecommerce.model.response.AuthenticateResponse;
import com.beeecommerce.service.AccountService;
import com.beeecommerce.service.AuthenticateService;
import com.beeecommerce.util.ObjectHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AuthenticateFacade {

    private final AuthenticateService authenticateService;

    private final AccountService accountService;

    public void register(RegisterRequest request) throws ApplicationException {

        ObjectHelper.checkNullParam(
                request.getUsername(),
                request.getConfirmPassword(),
                request.getEmail(),
                request.getFullname(),
                request.getPassword(),
                request.getPhoneNumber()
        );


        authenticateService.register(request);
    }

    public AuthenticateResponse checkLoginCredentials(AuthenticationRequest authenticationRequest)
            throws ApplicationException {

        ObjectHelper.checkNullParam(
                authenticationRequest.getUsername()
                , authenticationRequest.getPassword()
        );

        return authenticateService.login(authenticationRequest);
    }

    public void requestResetPassword(String username) throws ApplicationException {

        ObjectHelper.checkNullParam("Username is required", username);

        authenticateService.requestResetPassword(username);
    }

    public void resetPassword(AuthenticationRequest authenticationRequest)
            throws Exception {

        ObjectHelper.checkNullParam(
                authenticationRequest.getConfirmPassword()
                , authenticationRequest.getPassword()
                , authenticationRequest.getToken()
        );

        if (!authenticationRequest.getPassword().equals(authenticationRequest.getConfirmPassword())) {
            throw new AuthenticateException("Mật khẩu không trùng khớp!");
        }

        authenticateService.resetPassword(authenticationRequest);

    }

    public void confirmAccount(String token) throws ApplicationException {
        ObjectHelper.checkNullParam(token);

        authenticateService.confirmAccount(token);
    }


    public AuthenticateResponse authentication() throws AuthenticateException {
        return authenticateService.authentication();
    }

    public void logout() throws AuthenticateException {
        authenticateService.logout();
    }

    public String refreshToken(AuthenticationRequest authenticationRequest) throws ParamInvalidException, RefreshTokenNotFoundException {
        String refreshToken = authenticationRequest.getRefreshToken();
        ObjectHelper.checkNullParam("[refreshToken] is required", refreshToken);
        return authenticateService.refreshToken(refreshToken);

    }

    public void requestConfirmAccount(String username) throws ApplicationException {
        ObjectHelper.checkNullParam("Username is required", username);
        authenticateService.requestConfirmAccount(username);
    }


    public void updateAccountInfo(AccountRequest accountRequest, MultipartFile avatar) throws ApplicationException {
        accountService.updateAccountInfo(accountRequest,avatar );
    }

}

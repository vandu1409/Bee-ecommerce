package com.beeecommerce.controller;

import com.beeecommerce.exception.core.ApplicationException;
import com.beeecommerce.facade.AuthenticateFacade;
import com.beeecommerce.model.request.AccountRequest;
import com.beeecommerce.model.request.AuthenticationRequest;
import com.beeecommerce.model.request.RegisterRequest;
import com.beeecommerce.model.response.AuthenticateResponse;
import com.beeecommerce.model.response.common.ResponseHandler;
import com.beeecommerce.model.response.common.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.beeecommerce.constance.ApiPaths.AuthenticatePath.*;


@RestController
@RequestMapping(AUTH_PREFIX)
@RequiredArgsConstructor
public class AuthenticateController {

    private final AuthenticateFacade authenticateFacade;

    private final ObjectMapper objectMapper;

    @PostMapping(REGISTER)
    public ResponseObject<String> register(@RequestBody RegisterRequest request) throws ApplicationException {


        authenticateFacade.register(request);

        return ResponseHandler.response("Đăng ký thành công");

    }

    @PostMapping(LOGIN)
    public ResponseObject<AuthenticateResponse> login(@RequestBody AuthenticationRequest authenticationRequest)
            throws ApplicationException {
        return ResponseHandler.response(authenticateFacade. checkLoginCredentials(authenticationRequest));
    }

    @PostMapping(LOGOUT)
    public ResponseObject<?> logout()
            throws ApplicationException {
        authenticateFacade.logout();

        return ResponseHandler.message("Đăng xuất thành công!");
    }

    @PostMapping(REFRESH_TOKEN)
    public ResponseObject<String> refreshToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws ApplicationException {

        String newAccessToken = authenticateFacade.refreshToken(authenticationRequest);

        return ResponseHandler
                .response(newAccessToken)
                .message("Refresh token success!")
                ;
    }

    @PostMapping(REQUEST_RESET_PASSWORD)
    public ResponseObject<String> requestResetPassword(@RequestBody AuthenticationRequest authenticationRequest) throws ApplicationException {

        authenticateFacade.requestResetPassword(authenticationRequest.getUsername());

        return ResponseHandler.response("Quên mật khẩu thành công!Vui lòng kiểm tra email!");

    }

    @PostMapping(RESET_PASSWORD)
    public ResponseObject<String> resetPasword(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        authenticateFacade.resetPassword(authenticationRequest);
        return ResponseHandler.response("Đổi mật khẩu thành công!");
    }

    @PostMapping(CONFIRM_ACCOUNT)
    public ResponseObject<?> requestConfirmAccount(@RequestBody AuthenticationRequest authenticationRequest)
            throws ApplicationException {

        authenticateFacade.requestConfirmAccount(authenticationRequest.getUsername());

        return ResponseHandler.message("Vui lòng kiểm tra email để xác thực tài khoản!");

    }

    @PutMapping(CONFIRM_ACCOUNT)
    public ResponseObject<?> confirmAccount(@RequestBody AuthenticationRequest authenticationRequest)
            throws ApplicationException {

        authenticateFacade.confirmAccount(authenticationRequest.getToken());

        return ResponseHandler.message("Xác thực thành công!");

    }

    @PostMapping(VALIDATE)
    public ResponseObject<AuthenticateResponse> sendMail()
            throws ApplicationException {

        return ResponseHandler
                .response(authenticateFacade.authentication())
                .message("Authentication success!");
    }

    @PutMapping(UPDATE_ACCOUNT)
    @Operation(summary = "Update account info", description = "Endpoint này chỉ update được fullname, phoneNumber")
    public ResponseObject<?> update(
            @RequestParam(value = "data", required = false) String data,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar
    )
            throws ApplicationException, JsonProcessingException {

        AccountRequest accountReq = data != null
                ? objectMapper.readValue(data, AccountRequest.class)
                : new AccountRequest();
        authenticateFacade.updateAccountInfo(accountReq, avatar);
        return ResponseHandler.message("Upadate thành công!");
    }

}

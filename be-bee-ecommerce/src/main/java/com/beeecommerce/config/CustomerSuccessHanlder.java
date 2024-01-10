package com.beeecommerce.config;

import com.beeecommerce.entity.Account;
import com.beeecommerce.repository.AccountRepository;
import com.beeecommerce.service.CustomOAuth2UserService;
import com.beeecommerce.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomerSuccessHanlder implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final AccountRepository accountRepository;

    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${app.oauth2.google.return-url}")
    private String returnUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User oauth2User) {

                Account account = customOAuth2UserService.getOrCreateUser(oauth2User);

                String refreshToken = jwtService.generateRefreshToken(account);
                String accessToken = jwtService.generateAccessToken(account);

                String redirectUrl = returnUrl.formatted(refreshToken);
                response.sendRedirect(redirectUrl);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication không thành công");
            }
        }
    }


}

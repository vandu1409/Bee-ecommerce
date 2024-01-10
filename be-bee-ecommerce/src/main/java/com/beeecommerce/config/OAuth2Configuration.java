package com.beeecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Configuration
public class OAuth2Configuration {


    @Value("${app.oauth2.google.client-id}")
    private String clientId;

    @Value("${app.oauth2.google.client-secret}")
    private String clientSecret;

    @Value("${app.oauth2.google.redirect-uri}")
    private String redirectUri;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .clientName("Google")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("email", "profile")
                .userNameAttributeName("email")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://oauth2.googleapis.com/token")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}
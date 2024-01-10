package com.beeecommerce.oauth2;

import com.beeecommerce.exception.common.OAuth2AuthenticationProcessingException;
import com.beeecommerce.model.AuthProvider;
import com.beeecommerce.oauth2.user.GoogleOAuth2UserInfo;
import com.beeecommerce.oauth2.user.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
    }
}

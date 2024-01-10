package com.beeecommerce.service;

import com.beeecommerce.constance.Role;
import com.beeecommerce.entity.Account;
import com.beeecommerce.oauth2.OAuth2UserInfoFactory;
import com.beeecommerce.oauth2.user.GoogleOAuth2UserInfo;
import com.beeecommerce.oauth2.user.OAuth2UserInfo;
import com.beeecommerce.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        return oAuth2User;
    }


    public @NonNull Account getOrCreateUser(OAuth2User auth2User) {

        OAuth2UserInfo oAuth2UserInfo = new GoogleOAuth2UserInfo(auth2User.getAttributes());

        return accountRepository.findByEmail(oAuth2UserInfo.getEmail())
                .map(existingUser ->          updateExistingUser(existingUser, oAuth2UserInfo))
                .orElseGet(() -> createNewUser(oAuth2UserInfo));
    }

    private Account createNewUser(OAuth2UserInfo oAuth2UserInfo) {
        Account account = new Account();
        account.setEmail(oAuth2UserInfo.getEmail());
        account.setUsername(oAuth2UserInfo.getEmail());
        account.setFullname(oAuth2UserInfo.getName());
        account.setPassword(RandomStringUtils.randomAlphanumeric(20));
        account.setAvatar(oAuth2UserInfo.getImageUrl());
        account.setRole(Role.CUSTOMER);

        return accountRepository.save(account);
    }

    private Account updateExistingUser(Account existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setUsername(oAuth2UserInfo.getEmail());
        existingUser.setEmail(oAuth2UserInfo.getEmail());

        return accountRepository.save(existingUser);
    }


}



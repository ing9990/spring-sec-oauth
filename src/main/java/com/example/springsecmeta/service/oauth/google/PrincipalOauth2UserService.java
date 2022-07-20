package com.example.springsecmeta.service.oauth.google;

/**
 * @author Taewoo
 */

import com.example.springsecmeta.config.auth.PrincipalDetails;
import com.example.springsecmeta.config.auth.provider.GoogleUserInfo;
import com.example.springsecmeta.config.auth.provider.NaverUserInfo;
import com.example.springsecmeta.config.auth.provider.providerInterface.OAuth2UserInfo;
import com.example.springsecmeta.domain.User;
import com.example.springsecmeta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserInfo oAuth2UserInfo = null;
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String client = userRequest.getClientRegistration().getRegistrationId();

        if (client.equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (client.equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        } else if (client.equals("facebook")) {

        } else {
            System.out.println("지원하지 않는 Provider.");
        }


        // User Info Parsing
        String provider = oAuth2UserInfo.getProvider();  // google | kakao | facebook etc...
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId; // google_1098212392 | kakao_2321323 etc...
        String password = bCryptPasswordEncoder.encode("Hello, World");
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        User userEntity = userRepository.findByUsername(username);

        if (userEntity == null) {
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();

            userRepository.save(userEntity);
        } else {
            System.out.println("소셜로그인으로 인해 이미 가입된 사용자입니다.");
        }


        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}










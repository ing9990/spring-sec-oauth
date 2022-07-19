package com.example.springsecmeta.service.oauth.google;

/**
 * @author Taewoo
 */

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        var profile = super.loadUser(userRequest).getAttributes();

        var sub = profile.get("sub");
        var name = profile.get("name");
        var gname = profile.get("given_name");
        var fname = profile.get("family_name");
        var pic = profile.get("picture");

        System.out.println("google id:" + sub);
        System.out.println("google name: " + name);
        System.out.println("이름: " + gname);
        System.out.println("성: " + fname);
        System.out.println("프로필 사진: " + pic);

        return super.loadUser(userRequest);
    }


}

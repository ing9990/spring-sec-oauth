package com.example.springsecmeta.config.auth.provider.providerInterface;

/**
 * @author Taewoo
 */

public interface OAuth2UserInfo {
    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();
}

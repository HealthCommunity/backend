package com.spa.springCommuProject.config.login.provider;

import com.spa.springCommuProject.user.domain.Provider;

public interface OAuth2UserInfo {

    String getProviderId();

    Provider getProvider();

    String getEmail();

    String getName();

}

package com.spa.springCommuProject.config.login.provider;

import com.spa.springCommuProject.user.domain.Provider;
import java.util.Map;

public class FacebookUserInfo implements OAuth2UserInfo{

    private final Map<String, Object> attributes;

    public FacebookUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public Provider getProvider() {
        return Provider.FACEBOOK;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}

package com.spa.springCommuProject.user.domain;

public enum Provider {
    GOOGLE("google"),
    NAVER("naver"),
    FACEBOOK("facebook"),
    SELF("self");

    Provider(String name) {
        this.name = name;
    }

    private String name;
}

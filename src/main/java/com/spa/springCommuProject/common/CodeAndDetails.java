package com.spa.springCommuProject.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodeAndDetails {
    SUCCESS("20000", "성공");

    private final String code;
    private final String message;
}

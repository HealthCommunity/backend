package com.spa.springCommuProject.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserJoinDto {

    @NotBlank
    private String nickName;

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}

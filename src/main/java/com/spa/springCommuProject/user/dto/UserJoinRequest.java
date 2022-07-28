package com.spa.springCommuProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserJoinRequest {

    public UserJoinRequest(){}


    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    private String nickName;


}

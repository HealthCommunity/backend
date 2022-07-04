package com.spa.springCommuProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserLoginDTO {

    private String loginId;
    private String password;
}

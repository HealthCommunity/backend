package com.spa.springCommuProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserUpdateDto {

    @NotBlank
    private String nickName;

    @NotBlank
    private String password;


}

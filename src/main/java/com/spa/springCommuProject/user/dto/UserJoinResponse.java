package com.spa.springCommuProject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserJoinResponse {
    @NotBlank
    private Long id;

    @NotBlank
    private String nickName;

}

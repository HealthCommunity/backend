package com.spa.springCommuProject.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BigThreeDTO {

    @NotBlank
    private String squat;
    @NotBlank
    private String bench;
    @NotBlank
    private String dead;
}

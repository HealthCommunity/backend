package com.spa.springCommuProject.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BigThreeDTO {

    @NotBlank
    private int squat;
    @NotBlank
    private int bench;
    @NotBlank
    private int dead;
}

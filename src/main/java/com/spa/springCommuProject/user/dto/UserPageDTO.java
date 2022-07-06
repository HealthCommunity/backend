package com.spa.springCommuProject.user.dto;

import com.spa.springCommuProject.user.domain.BigThreePower;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserPageDTO {

    @NotBlank
    private String nickName;
    @NotBlank
    private String loginId;
    @NotBlank
    private BigThreePower bigThreePower;

}

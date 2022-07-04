package com.spa.springCommuProject.user.dto;

import com.spa.springCommuProject.user.domain.BigThreePower;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPageDto {

    private String nickName;
    private String loginId;
    private BigThreePower bigThreePower;

}

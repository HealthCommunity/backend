package com.spa.springCommuProject.user.dto;

import com.spa.springCommuProject.user.domain.BigThreePower;
import com.spa.springCommuProject.user.domain.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String nickName;

    private String loginId;
    private String password;

    private Role role;

    private BigThreePower bigThreePower;

}

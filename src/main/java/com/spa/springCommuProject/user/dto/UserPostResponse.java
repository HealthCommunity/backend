package com.spa.springCommuProject.user.dto;

import com.spa.springCommuProject.user.domain.BigThreePower;
import com.spa.springCommuProject.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPostResponse {

    private Long userId;

    private String nickName;

    private BigThreePower bigThreePower;

    private Role role;
}

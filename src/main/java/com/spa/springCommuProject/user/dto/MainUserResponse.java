package com.spa.springCommuProject.user.dto;

import com.spa.springCommuProject.user.domain.BigThreePower;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainUserResponse {
    private String userNickName;
    private BigThreePower bigThreePower;
}

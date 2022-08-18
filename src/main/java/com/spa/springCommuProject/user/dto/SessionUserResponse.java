package com.spa.springCommuProject.user.dto;

import com.spa.springCommuProject.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionUserResponse {

    private Long userId;

    private Role role;
}

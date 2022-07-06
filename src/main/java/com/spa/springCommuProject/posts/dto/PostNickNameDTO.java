package com.spa.springCommuProject.posts.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostNickNameDTO {

    /**
     * 닉네임만 필요할때 사용하는 DTO
     */

    @NotBlank
    private String nickName;

    public PostNickNameDTO(String nickName) {
        this.nickName = nickName;
    }
}

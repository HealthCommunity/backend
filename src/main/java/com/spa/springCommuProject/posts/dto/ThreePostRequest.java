package com.spa.springCommuProject.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ThreePostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String nickName;

    private Long userId;

    private MultipartFile bench;

    private MultipartFile squat;

    private MultipartFile dead;
}

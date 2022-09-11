package com.spa.springCommuProject.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ThreePostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private MultipartFile bench;

    private MultipartFile squat;

    private MultipartFile dead;

    private List<MultipartFile> thumbnails = new ArrayList<>();
}

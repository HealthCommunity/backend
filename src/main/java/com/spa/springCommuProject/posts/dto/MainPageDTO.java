package com.spa.springCommuProject.posts.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MainPageDTO {
    private List<MainPostResponse> freeposts = new ArrayList<>();
    private List<MainPostResponse> threepowerposts = new ArrayList<>();
    private List<MainPostResponse> exerciseposts = new ArrayList<>();

}

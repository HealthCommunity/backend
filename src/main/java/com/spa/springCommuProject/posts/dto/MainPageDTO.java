package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.user.dto.MainUserResponse;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MainPageDTO {
    private List<MainUserResponse> users = new ArrayList<>();
    private List<MainPostResponse> freeposts = new ArrayList<>();
    private List<MainPostResponse> threepowerposts = new ArrayList<>();
    private List<MainPostResponse> exerciseposts = new ArrayList<>();

}

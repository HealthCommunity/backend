package com.spa.springCommuProject.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainPostResponse {
    private Long postId;
    private String title;
    private String nickName;
    private int view;
}

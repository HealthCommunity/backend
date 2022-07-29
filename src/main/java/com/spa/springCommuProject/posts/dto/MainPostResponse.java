package com.spa.springCommuProject.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MainPostResponse {
    private Long id;
    private String title;
    private String nickName;
    private int view;
}

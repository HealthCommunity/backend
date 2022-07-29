package com.spa.springCommuProject.posts.dto;

import lombok.Data;

@Data
public class SearchRequest {
    private String select;
    private String keyword;
}

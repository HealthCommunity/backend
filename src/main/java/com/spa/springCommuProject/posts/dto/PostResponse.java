package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.posts.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostResponse {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String nickName;

    private List<String> urls = new ArrayList<>();

    private int view;

    private LocalDateTime createdDate;

    public PostResponse(Post post,  String nickName, List<String> urls) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.nickName = nickName;
        this.view = post.getView();
        this.urls =urls;
    }
}

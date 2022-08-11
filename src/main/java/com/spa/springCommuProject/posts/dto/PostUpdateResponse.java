package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.posts.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostUpdateResponse {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private List<String> urls = new ArrayList<>();


    public PostUpdateResponse(Post post, List<String> urls) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.urls =urls;
    }
}

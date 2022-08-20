package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.user.dto.SessionUserResponse;
import com.spa.springCommuProject.user.dto.UserPostResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostResponse {

    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private UserPostResponse UserPostResponse;

    private SessionUserResponse sessionUserResponse;

    private List<String> urls = new ArrayList<>();

    private int view;

    private String  createdDate;

    public PostResponse(Post post, List<String> urls) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.createdDate = post.getCreatedDate().format(myPattern);
        this.UserPostResponse = post.getUser().convertToUserPostResponse();
        this.view = post.getView();
        this.urls =urls;
    }

    public PostResponse(Post post, List<String> urls, SessionUserResponse sessionUserResponse) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.createdDate = post.getCreatedDate().format(myPattern);
        this.UserPostResponse = post.getUser().convertToUserPostResponse();
        this.sessionUserResponse = sessionUserResponse;
        this.view = post.getView();
        this.urls =urls;
    }
}

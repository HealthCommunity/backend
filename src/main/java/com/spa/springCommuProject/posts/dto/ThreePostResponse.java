package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.posts.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class ThreePostResponse {

    private Long postId;
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String  createdDate;

    private String nickName;

    private int view;

    private String benchUrl;
    private String squatUrl;
    private String deadUrl;

    private Long userId;

    public ThreePostResponse(Post post, String benchUrl, String squatUrl, String deadUrl) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.createdDate = post.getCreatedDate().format(myPattern);
        this.nickName = post.getUser().getNickName();
        this.view = post.getView();
        this.benchUrl = benchUrl;
        this.squatUrl = squatUrl;
        this.deadUrl = deadUrl;
        this.userId = post.getUser().getId();
    }
}

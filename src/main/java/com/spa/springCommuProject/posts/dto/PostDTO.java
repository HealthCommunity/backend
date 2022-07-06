package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.user.domain.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class PostDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private LocalDateTime createdDate;

    private User user;

    public PostDTO(String title, String content, LocalDateTime createdDate, User user) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.user = user;
    }
}

package com.spa.springCommuProject.comment.domain;

import com.spa.springCommuProject.comment.dto.CommentResponseDto;
import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String comment;

    @Builder.Default
    private LocalDateTime createdDate = Post.getNow();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public CommentResponseDto convertToCommentResponseDto() {
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return new CommentResponseDto(this.id, this.comment, Post.getNow().format(myPattern), this.user.getNickName(), this.posts.getId());
    }
}

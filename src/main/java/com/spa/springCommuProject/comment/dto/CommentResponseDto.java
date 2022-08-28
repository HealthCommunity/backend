package com.spa.springCommuProject.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String createdDate;
    private String nickName;
    private Long postsId;

}

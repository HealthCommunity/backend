package com.spa.springCommuProject.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostViewDTO {
    /**
     * post관련 정보 목록에서 보기만할때 넘겨주는 DTO 아마 거의 파일만 빼고..?
     */

    private Long postId;

    private String title;

    private String createdDate;

    private List<String> urls = new ArrayList<>();

    private String nickname;

    private int view;

    public PostViewDTO(Long postId, String title, LocalDateTime createdDate, String nickname, int view) {
        DateTimeFormatter myPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.postId = postId;
        this.title = title;
        this.createdDate = createdDate.format(myPattern);
        this.nickname = nickname;
        this.view = view;
    }
}

package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostViewDTO {
    /**
     * post관련 정보 목록에서 보기만할때 넘겨주는 DTO 아마 거의 파일만 빼고..?
     */

    @NotBlank
    private String title;

    private LocalDateTime createdDate;

    private String nickname;

    private int view;

}

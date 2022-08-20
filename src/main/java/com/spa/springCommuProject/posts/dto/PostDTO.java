package com.spa.springCommuProject.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostDTO {
    /**
     * 저장이나 수정할때 모든 정보가 필요할 때 사용하는 DTO
     */
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private LocalDateTime createdDate;

    private List<MultipartFile> files;

    private Long userId;
    private String nickname;

    private int view;

    public PostDTO() {
    }

    public PostDTO(String title, String content, LocalDateTime createdDate, List<MultipartFile> files, String nickname, int view) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.files = files;
        this.nickname = nickname;
        this.view = view;
    }


}

package com.spa.springCommuProject.posts.dto;

import com.spa.springCommuProject.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private User user;

    private int view;

    public PostDTO() {
    }

    public PostDTO(String title, String content, LocalDateTime createdDate, User user, int view) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.user = user;
        this.view = view;
    }

    public PostDTO(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static LocalDateTime getNow(){
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return LocalDateTime.parse(nowTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}

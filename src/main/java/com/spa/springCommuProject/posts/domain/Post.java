package com.spa.springCommuProject.posts.domain;

import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<File> photo = new ArrayList<>();

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private String title;
    private String content;

    private Boolean available;
    private int view;

    private PostCategory postCategory;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedDate = LocalDateTime.parse(nowTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    public void delete(){
        this.available = false;
    }

    public void viewIncrease(){
        this.view++;
    }

    public PostDTO convertToDTO() {
        return new PostDTO(this.title, this.content, this.createdDate, this.user);
    }
}

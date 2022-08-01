package com.spa.springCommuProject.posts.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spa.springCommuProject.file.domain.FileDetail;
import com.spa.springCommuProject.posts.dto.MainPostResponse;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.user.domain.User;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    protected Post() {
    }

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<FileDetail> files = new ArrayList<>();

    @Builder.Default
    private LocalDateTime createdDate = Post.getNow();

    private String title;
    private String content;

    @Builder.Default
    private int view = 0;

    @Builder.Default
    private int likes = 0;

    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    public Post(User user, String title, String content, PostCategory postCategory) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.postCategory = postCategory;
    }

    public Post update(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }

    public void viewIncrease(){
        this.view++;
    }

    public PostDTO convertToDTO() { //파일 추가돼야함 나중에
        return new PostDTO(this.title, this.content, this.createdDate, null, this.user.getNickName(), this.view);
    }

    public PostViewDTO convertToViewDTO(){
        return new PostViewDTO(this.id, this.title, this.createdDate, this.user.getNickName(), this.view);
    }

    public PostNickNameDTO convertToNickNameDTO(){
        return new PostNickNameDTO(this.user.getNickName());
    }

    public static LocalDateTime getNow(){
        String nowTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return LocalDateTime.parse(nowTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public MainPostResponse convertToMainPostResponse(){
        return new MainPostResponse(this.id, this.title, this.user.getNickName(), this.getView());
    }

}

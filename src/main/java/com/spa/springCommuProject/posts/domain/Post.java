package com.spa.springCommuProject.posts.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<File> files = new ArrayList<>();

    private LocalDateTime createdDate;

    private String title;
    private String content;

    private int view;

    @Enumerated(EnumType.STRING)
    private PostCategory postCategory;

    public Post update(String title, String content){
        this.title = title;
        this.content = content;
        return this;
    }

    public void viewIncrease(){
        this.view++;
    }

    public PostDTO convertToDTO() { //파일 추가돼야함 나중에
        return new PostDTO(this.title, this.content, this.createdDate, this.user, this.view);
    }

    public PostViewDTO convertToViewDTO(){
        return new PostViewDTO(this.title, this.content, this.createdDate, this.user, this.view);
    }

    public PostNickNameDTO convertToNickNameDTO(){
        return new PostNickNameDTO(this.user.getNickName());
    }

}

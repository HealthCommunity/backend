package com.spa.springCommuProject.posts.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Builder
public class File {

    protected File() {
    }

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String uploadFileName;
    private String storeFileName;

    private FileCategory fileCategory;
}

package com.spa.springCommuProject.posts.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
public class File {

    protected File() {
    }

    @Id
    @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    private String uploadFileName;
    private String storeFileName;

    @Enumerated(EnumType.STRING)
    private FileCategory fileCategory;
}

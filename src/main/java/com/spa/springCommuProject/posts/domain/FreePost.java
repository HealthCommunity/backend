package com.spa.springCommuProject.posts.domain;

import com.spa.springCommuProject.user.domain.User;
import lombok.Getter;

import javax.persistence.Entity;


@Getter
@Entity
public class FreePost extends Post {

    public FreePost() {

    }

    public FreePost(User user, String title, String content) {
        super(user, title, content);
    }


}

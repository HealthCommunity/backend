package com.spa.springCommuProject.posts.service;

import com.spa.springCommuProject.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostService postService;
    @Autowired UserService userService;

    @Test
    public void findPostsByUserId() throws Exception{
        //given

        //when
//        List<Post> posts = userService.findAllPostsByUserId(1L);
//
//        //then
//        Assertions.assertThat(2).isEqualTo(posts.size());
    }

    @Test
    public void findPostsByUser() throws Exception{
        //given
//        List<Post> posts = userService.findAllPostsByUserId(1L);
//
//        User findUser = userService.findById(1L);
//
//        Post post = posts.get(0);
//
//        //when
//
//        //then
//        Assertions.assertThat(post.getUser().getId()).isEqualTo(findUser.getId());
//
//        System.out.println(posts);
    }
}
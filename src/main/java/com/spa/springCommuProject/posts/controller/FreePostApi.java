package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/FreePost")
public class FreePostApi {

    private final PostService postService;

//    @GetMapping("/list/{pageNum}")
//    public ResponseEntity freePostListPage(@PathVariable int page) {
//        //int size = postService.findAvailableFreePosts().size()/10 + 1;
//        List<FreePost> freePosts = postService.findAvailablePagingFreePosts(page);
//
//    }

}

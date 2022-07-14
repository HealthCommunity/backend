package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.*;
import com.spa.springCommuProject.posts.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/freepost")
public class FreePostApi {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 자유게시판 목록")
    public ResponseEntity<Page<PostViewDTO>> freePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        PageRequest pageRequest =PageRequest.of(page, size, Sort.by("id").descending());
        Page<PostViewDTO> pagingPostsAndCount = postService.findPagingPosts(PostCategory.FREEPOST, pageRequest);
        return new ResponseEntity<>(pagingPostsAndCount, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "자유게시글 생성")
    public ResponseEntity<PostResponse> createFreePost(PostRequest postRequest){
        return new ResponseEntity<>(postService.save(postRequest, PostCategory.FREEPOST), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "자유게시글 보기 페이지")
    public ResponseEntity<PostDTO> freePostView(@PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "자유게시글 수정 폼")
    public ResponseEntity<PostDTO> editFreePostForm(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/edit")
    @ApiOperation(value = "자유게시글 수정")
    public ResponseEntity<PostDTO> editFreePost(@PathVariable Long postId,
                               @Valid PostDTO postDTO) {
        PostDTO updatePostDTO = postService.updatePost(postId, postDTO);

        return new ResponseEntity<>(updatePostDTO, HttpStatus.OK);
    }

    @GetMapping("/{postId}/delete")
    @ApiOperation(value = "자유게시글 삭제 폼") //닉네임만 넘겨주기 ~님 정말 삭제하시겠습니까?
    public ResponseEntity<PostNickNameDTO> deleteFreePostForm(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.findNickNameById(postId), HttpStatus.OK);
    }

    @PostMapping("{postId}/delete")
    @ApiOperation(value = "자유게시글 삭제")
    public ResponseEntity deleteFreePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

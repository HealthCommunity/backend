package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.file.service.FileService;
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
@RequestMapping("/api/threepowerPost")
public class ThreePowerPostApi {

    private final PostService postService;
    private final FileService fileService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 삼대력게시판 목록")
    public ResponseEntity<Page<PostViewDTO>> threePowerPostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        PageRequest pageRequest =PageRequest.of(page, size, Sort.by("id").descending());
        Page<PostViewDTO> pagingPostsAndCount = postService.findPagingPosts(PostCategory.THREEPOWERPOST, pageRequest);
        return new ResponseEntity<>(pagingPostsAndCount, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "삼대력게시글 생성")
    public ResponseEntity<ThreePostResponse> createThreePowerPost(ThreePostRequest threePostRequest){
        return new ResponseEntity<>(postService.threeSave(threePostRequest, PostCategory.THREEPOWERPOST), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "삼대력게시글 보기 페이지")
    public ResponseEntity<PostDTO> threePowerPostView(@PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "삼대력게시글 수정 폼")
    public ResponseEntity<PostDTO> editThreePowerPostForm(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/edit")
    @ApiOperation(value = "삼대력게시글 수정")
    public ResponseEntity<PostDTO> editThreePowerPost(@PathVariable Long postId,
                               @Valid PostDTO postDTO) {
        PostDTO updatePostDTO = postService.updatePost(postId, postDTO);

        return new ResponseEntity<>(updatePostDTO, HttpStatus.OK);
    }

    @GetMapping("/{postId}/delete")
    @ApiOperation(value = "삼대력게시글 삭제 폼") //닉네임만 넘겨주기 ~님 정말 삭제하시겠습니까?
    public ResponseEntity<PostNickNameDTO> deleteThreePowerPostForm(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.findNickNameById(postId), HttpStatus.OK);
    }

    @PostMapping("{postId}/delete")
    @ApiOperation(value = "삼대력게시글 삭제")
    public ResponseEntity deleteThreePowerPost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.common.CommonResponse;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.*;
import com.spa.springCommuProject.posts.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/freepost")
public class FreePostApi {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 자유게시판 목록")
    public ResponseEntity<CommonResponse<List<PostViewDTO>>> freePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        List<PostViewDTO> pagingPostsAndCount = postService.findPagingPosts(PostCategory.FREEPOST, page, size);
        return ResponseEntity.ok(CommonResponse.from(pagingPostsAndCount));
    }

    @PostMapping()
    @ApiOperation(value = "자유게시글 생성")
    public ResponseEntity<CommonResponse<PostResponse>> createFreePost(PostRequest postRequest){
        return ResponseEntity.ok(CommonResponse.from(postService.save(postRequest, PostCategory.FREEPOST)));
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "자유게시글 보기 페이지")
    public ResponseEntity<CommonResponse<PostResponse>> freePostView(@PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId)));
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "자유게시글 수정 폼")
    public ResponseEntity<CommonResponse<PostResponse>> editFreePostForm(@PathVariable Long postId) {
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId)));
    }

    @PostMapping("/{postId}/edit")
    @ApiOperation(value = "자유게시글 수정")
    public ResponseEntity<CommonResponse<PostDTO>> editFreePost(@PathVariable Long postId,
                               @Valid PostDTO postDTO) {
        PostDTO updatePostDTO = postService.updatePost(postId, postDTO);

        return ResponseEntity.ok(CommonResponse.from(updatePostDTO));
    }

    @GetMapping("/{postId}/delete")
    @ApiOperation(value = "자유게시글 삭제 폼") //닉네임만 넘겨주기 ~님 정말 삭제하시겠습니까?
    public ResponseEntity<CommonResponse<PostNickNameDTO>> deleteFreePostForm(@PathVariable Long postId) {
        return ResponseEntity.ok(CommonResponse.from(postService.findNickNameById(postId)));
    }

    @PostMapping("{postId}/delete")
    @ApiOperation(value = "자유게시글 삭제")
    public ResponseEntity<Void> deleteFreePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

}

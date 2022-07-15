package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.common.CommonResponse;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
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
@RequestMapping("/api/freePost")
public class FreePostApi {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 자유게시판 목록")
    public ResponseEntity<CommonResponse<Page<PostViewDTO>>> freePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        PageRequest pageRequest =PageRequest.of(page, size, Sort.by("id").descending());
        Page<PostViewDTO> pagingPostsAndCount = postService.findPagingPosts(PostCategory.FREEPOST, pageRequest);
        return ResponseEntity.ok(CommonResponse.from(pagingPostsAndCount));
    }

    @PostMapping()
    @ApiOperation(value = "자유게시글 생성")
    public ResponseEntity<CommonResponse<PostDTO>> createFreePost(PostDTO postDTO){
        return ResponseEntity.ok(CommonResponse.from(postService.save(postDTO, PostCategory.FREEPOST)));
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "자유게시글 보기 페이지")
    public ResponseEntity<CommonResponse<PostDTO>> freePostView(@PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId)));
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "자유게시글 수정 폼")
    public ResponseEntity<CommonResponse<PostDTO>> editFreePostForm(@PathVariable Long postId) {
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

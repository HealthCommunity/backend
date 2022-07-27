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
@RequestMapping("/api/exercisepost")
public class ExercisePostApi {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 운동게시판 목록")
    public ResponseEntity<CommonResponse<List<PostViewDTO>>> exercisePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        List<PostViewDTO> posts = postService.findPagingPosts(PostCategory.EXERCISEPOST, page, size);
        return ResponseEntity.ok(CommonResponse.from(posts));
    }

    @PostMapping()
    @ApiOperation(value = "운동게시글 생성")
    public ResponseEntity<CommonResponse<PostResponse>> createExercisePost(PostRequest postRequest){
        return ResponseEntity.ok(CommonResponse.from(postService.save(postRequest, PostCategory.EXERCISEPOST)));
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "운동게시글 보기 페이지")
    public ResponseEntity<CommonResponse<PostResponse>> exercisePostView(@PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요(IP관련?)
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId)));
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "운동게시글 수정 폼")
    public ResponseEntity<CommonResponse<PostResponse>> editExercisePostForm(@PathVariable Long postId) {
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId)));
    }

    @PostMapping("/{postId}/edit")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<CommonResponse<PostDTO>> editExercisePost(@PathVariable Long postId,
                               @Valid PostDTO postDTO) {
        PostDTO updatePostDTO = postService.updatePost(postId, postDTO);

        return ResponseEntity.ok(CommonResponse.from(updatePostDTO));
    }

    @GetMapping("/{postId}/delete")
    @ApiOperation(value = "운동게시글 삭제 폼") //닉네임만 넘겨주기 ~님 정말 삭제하시겠습니까?
    public ResponseEntity<CommonResponse<PostNickNameDTO>> deleteExercisePostForm(@PathVariable Long postId) {
        return ResponseEntity.ok(CommonResponse.from(postService.findNickNameById(postId)));
    }

    @PostMapping("{postId}/delete")
    @ApiOperation(value = "운동게시글 삭제")
    public ResponseEntity<Void> deleteExercisePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

}

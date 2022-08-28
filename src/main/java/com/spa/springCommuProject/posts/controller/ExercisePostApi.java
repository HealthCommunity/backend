package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.common.CommonResponse;
import com.spa.springCommuProject.config.login.PrincipalUserDetails;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.*;
import com.spa.springCommuProject.posts.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercisepost")
public class ExercisePostApi {

    private final PostService postService;

    @GetMapping()
    @ApiOperation(value = "페이징된 운동게시판 목록")
    public ResponseEntity<CommonResponse<List<PostViewDTO>>> exercisePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        List<PostViewDTO> posts = postService.findPagingPosts(PostCategory.EXERCISEPOST, page, size);
        return ResponseEntity.ok(CommonResponse.from(posts));
    }

    @PostMapping()
    @ApiOperation(value = "운동게시글 생성")
    public ResponseEntity<CommonResponse<Void>> createExercisePost(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                                                           @Valid PostRequest postRequest){
        postService.save(postRequest, PostCategory.EXERCISEPOST, principalUserDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "운동게시글 보기 페이지")
    public ResponseEntity<CommonResponse<PostResponse>> exercisePostView(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                                                         @PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요(IP관련?)
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId, principalUserDetails)));
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "운동게시글 수정 폼")
    public ResponseEntity<CommonResponse<PostUpdateResponse>> editExercisePostForm(
        @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @PathVariable Long postId) {
        postService.validateUser(postId, principalUserDetails.getUser().getId());
        return ResponseEntity.ok(CommonResponse.from(postService.findUpdatePostById(postId)));
    }

    @PostMapping("/{postId}/edit")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<CommonResponse<Void>> editExercisePost(
        @AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
        @PathVariable Long postId,
        @Valid PostRequest postRequest) {
        postService.validateUser(postId, principalUserDetails.getUser().getId());
        postService.updatePost(postId, postRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/delete")
    @ApiOperation(value = "운동게시글 삭제 폼") //닉네임만 넘겨주기 ~님 정말 삭제하시겠습니까?
    public ResponseEntity<CommonResponse<PostNickNameDTO>> deleteExercisePostForm(
        @AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @PathVariable Long postId) {
        postService.validateUser(postId, principalUserDetails.getUser().getId());
        return ResponseEntity.ok(CommonResponse.from(postService.findNickNameById(postId)));
    }

    @PostMapping("/{postId}/delete")
    @ApiOperation(value = "운동게시글 삭제")
    public ResponseEntity<Void> deleteExercisePost(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
        @PathVariable Long postId) {
        postService.validateUser(postId, principalUserDetails.getUser().getId());
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/popular")
    @ApiOperation(value = "운동게시판 인기순 조회")
    public ResponseEntity<CommonResponse<List<PostViewDTO>>> exercisePostViewDesc() {
        List<PostViewDTO> posts = postService.popularExercisePost();
        return ResponseEntity.ok(CommonResponse.from(posts));
    }


}

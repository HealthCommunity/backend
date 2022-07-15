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
@RequestMapping("/api/exerciseePost")
public class ExercisePostApi {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 운동게시판 목록")
    public ResponseEntity<CommonResponse<Page<PostViewDTO>>> exercisePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        PageRequest pageRequest =PageRequest.of(page, size, Sort.by("id").descending());
        Page<PostViewDTO> pagingPostsAndCount = postService.findPagingPosts(PostCategory.EXERCISEPOST, pageRequest);
        return ResponseEntity.ok(CommonResponse.from(pagingPostsAndCount));
    }

    @PostMapping()
    @ApiOperation(value = "운동게시글 생성")
    public ResponseEntity<CommonResponse<PostDTO>> createExcercisePost(PostDTO postDTO){
        return ResponseEntity.ok(CommonResponse.from(postService.save(postDTO, PostCategory.EXERCISEPOST)));
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "운동게시글 보기 페이지")
    public ResponseEntity<CommonResponse<PostDTO>> excercisePostView(@PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요(IP관련?)
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId)));
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "운동게시글 수정 폼")
    public ResponseEntity<CommonResponse<PostDTO>> editExcercisePostForm(@PathVariable Long postId) {
        return ResponseEntity.ok(CommonResponse.from(postService.findPostById(postId)));
    }

    @PostMapping("/{postId}/edit")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<CommonResponse<PostDTO>> editExcercisePost(@PathVariable Long postId,
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

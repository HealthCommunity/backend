package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.posts.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exerciseePost")
public class ExercisePostApi {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 운동게시판 목록")
    public ResponseEntity<Page<PostViewDTO>> exercisePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        PageRequest pageRequest =PageRequest.of(page, size); //page * size를 어디서 해야하는가 이부분 다음에 이야기
        Page<PostViewDTO> pagingPostsAndCount = postService.findPagingPostsAndCount(PostCategory.EXERCISEPOST, pageRequest);
        return new ResponseEntity<>(pagingPostsAndCount, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "운동게시글 생성")
    public ResponseEntity<PostDTO> createExcercisePost(PostDTO postDTO){
        return new ResponseEntity<>(postService.save(postDTO, PostCategory.EXERCISEPOST), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    @ApiOperation(value = "운동게시글 보기 페이지")
    public ResponseEntity<PostDTO> excercisePostView(@PathVariable Long postId) {
        postService.viewIncrease(postId);  //조회 수 증가 But 새로고침할때마다 계속오름 logic필요(IP관련?)
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/edit")
    @ApiOperation(value = "운동게시글 수정 폼")
    public ResponseEntity<PostDTO> editExcercisePostForm(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.findPostById(postId), HttpStatus.OK);
    }

    @PostMapping("/{postId}/edit")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<PostDTO> editExcercisePost(@PathVariable Long postId,
                               @Valid PostDTO postDTO) {
        PostDTO updatePostDTO = postService.updatePost(postId, postDTO);

        return new ResponseEntity<>(updatePostDTO, HttpStatus.OK);
    }

    @GetMapping("/{postId}/delete")
    @ApiOperation(value = "운동게시글 삭제 폼") //닉네임만 넘겨주기 ~님 정말 삭제하시겠습니까?
    public ResponseEntity<PostNickNameDTO> deleteExercisePostForm(@PathVariable Long postId) {
        return new ResponseEntity<>(postService.findNickNameById(postId), HttpStatus.OK);
    }

    @PostMapping("{postId}/delete")
    @ApiOperation(value = "운동게시글 삭제")
    public ResponseEntity deleteExercisePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

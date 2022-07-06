package com.spa.springCommuProject.posts.controller;

import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.posts.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/freePost")
public class FreePostApi {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation(value = "페이징된 자유게시판 목록")
    public ResponseEntity<Map<String, Object>> freePostListPage(@RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        Map<String, Object> map = new HashMap<>();
        PageRequest pageRequest =PageRequest.of(page, size); //page * size를 어디서 해야하는가 이부분 다음에 이야기
        int count = postService.PostsCount(PostCategory.FREEPOST); //자유게시판 총 개수 프런트에 넘겨줘야 페이지 개수 만들수 있지 않을까
        List<PostViewDTO> freePosts = postService.findPagingPosts(PostCategory.FREEPOST, pageRequest);

        map.put("freePostCount", count);
        map.put("freePosts", freePosts);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "자유게시글 생성")
    public ResponseEntity<PostDTO> createFreePost(PostDTO postDTO){
        return new ResponseEntity<>(postService.save(postDTO, PostCategory.FREEPOST), HttpStatus.OK);
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
        postService.DeletePost(postId);
        return new ResponseEntity(HttpStatus.OK);
    }

}

package com.spa.springCommuProject.comment.controller;

import com.spa.springCommuProject.comment.dto.CommentRequestDto;
import com.spa.springCommuProject.comment.dto.CommentResponseDto;
import com.spa.springCommuProject.comment.service.CommentService;
import com.spa.springCommuProject.common.CommonResponse;
import com.spa.springCommuProject.config.login.PrincipalUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentApi {

    private final CommentService commentService;     /* CREATE */

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommonResponse<CommentResponseDto>> commentSave(@PathVariable Long postId, @RequestBody CommentRequestDto dto, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        return ResponseEntity.ok(CommonResponse.from(commentService.commentSave(principalUserDetails.getUser(), postId, dto)));
    }

    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> commentList(@PathVariable Long postId) {
        return ResponseEntity.ok(CommonResponse.from(commentService.commentList(postId)));
    }

    @PostMapping("/comment/{commentId}/delete")
    public ResponseEntity<Void> commentDelete(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/comment/{commentId}/edit")
    public ResponseEntity<Void> commentUpdate(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
        commentService.updateComment(commentId, dto);
        return ResponseEntity.ok().build();
    }


}

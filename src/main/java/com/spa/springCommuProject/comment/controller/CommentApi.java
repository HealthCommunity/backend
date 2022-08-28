package com.spa.springCommuProject.comment.controller;

import com.spa.springCommuProject.comment.dto.CommentRequestDto;
import com.spa.springCommuProject.comment.service.CommentService;
import com.spa.springCommuProject.config.login.PrincipalUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;     /* CREATE */

    @PostMapping("/api/post/{id}/comments")
    public ResponseEntity<Void> commentSave(@PathVariable Long id, @RequestBody CommentRequestDto dto, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        commentService.commentSave(principalUserDetails.getUser(), id, dto);
        return ResponseEntity.ok().build();
    }

}

package com.spa.springCommuProject.comment.service;

import com.spa.springCommuProject.comment.domain.Comment;
import com.spa.springCommuProject.comment.dto.CommentRequestDto;
import com.spa.springCommuProject.comment.dto.CommentResponseDto;
import com.spa.springCommuProject.comment.repository.CommentRepository;
import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postsRepository;

    /* CREATE */
    @Transactional
    public CommentResponseDto commentSave(User user, Long id, CommentRequestDto dto) {
        Post post = postsRepository.findById(id).get();
        Comment comment = Comment.builder()
                .comment(dto.getComment())
                .posts(post)
                .user(user).build();
        commentRepository.save(comment);
        return comment.convertToCommentResponseDto();
    }

    public List<CommentResponseDto> commentList(Long id){
        Post findPost = postsRepository.getById(id);
        return findPost.getComments().stream().map(Comment::convertToCommentResponseDto).collect(
            Collectors.toList());
    }

}

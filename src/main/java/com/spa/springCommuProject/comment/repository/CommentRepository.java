package com.spa.springCommuProject.comment.repository;

import com.spa.springCommuProject.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

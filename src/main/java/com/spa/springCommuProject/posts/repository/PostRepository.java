package com.spa.springCommuProject.posts.repository;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByPostCategory(PostCategory postCategory,
                                  Pageable pageable); //페이징 기능

    Page<Post> findByUserOrderByCreatedDateDesc(User user, Pageable pageable);
}

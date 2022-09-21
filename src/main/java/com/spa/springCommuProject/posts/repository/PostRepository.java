package com.spa.springCommuProject.posts.repository;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPostCategory(PostCategory postCategory,
                                  Pageable pageable); //페이징 기능

    List<Post> findByPostCategoryOrderByViewDesc(PostCategory postCategory); //메인화면 조회수 상위 10개 사진

    List<Post> findByUserOrderByCreatedDateDesc(User user, Pageable pageable);

    // findByXXX: XXX 컬럼을 키워드로 검색
    // Containing: 특정 키워드 포함 여부
    //IgnoreCase 키워드는 대소문자 구별을 하지 않는다
    List<Post> findByTitleContainingIgnoreCaseOrderByCreatedDateDesc(String title);

    List<Post> findByContentContainingIgnoreCaseOrTitleContainingIgnoreCaseOrderByCreatedDateDesc(String title, String content);

    List<Post> findByContentContainingIgnoreCaseOrderByCreatedDateDesc(String content);

    List<Post> findByUser(User user);
}

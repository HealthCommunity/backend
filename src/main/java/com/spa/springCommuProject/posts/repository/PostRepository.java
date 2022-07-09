package com.spa.springCommuProject.posts.repository;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPostCategory(PostCategory postCategory);

    //@Query("select p from Post p where p.postCategory = :postCategory order by p.createdDate desc")
    Page<Post> findByPostCategoryOrderByCreatedDateDesc(PostCategory postCategory,
                                                        Pageable pageable); //페이징 기능

//    @Query("select p from Post p join fetch p.user where p.user.id = :userId order by p.createdDate desc")
//    Page<Post> findsByUserOrderByCreatedDateDesc(@Param("userId") Long userId, Pageable pageable);
}

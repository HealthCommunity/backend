package com.spa.springCommuProject.posts.repository;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPostCategory(PostCategory postCategory);

    //@Query("select p from Post p where p.postCategory = :postCategory order by p.createdDate desc")
    List<Post> findByPostCategoryOrderByCreatedDateDesc(PostCategory postCategory,
                                                        Pageable pageable); //페이징 기능

}

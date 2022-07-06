package com.spa.springCommuProject.posts.service;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.posts.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public List<PostDTO> findAllPosts(PostCategory postCategory) {
        List<Post> posts = postRepository.findByPostCategory(postCategory);
        return posts.stream().
                map(Post::convertToDTO).
                collect(Collectors.toList());
    }

    public int PostsCount(PostCategory postCategory) {
        return postRepository.findByPostCategory(postCategory).size();
    }

    public List<PostViewDTO> findPagingPosts(PostCategory postCategory, Pageable page) {
        List<Post> posts = postRepository.findByPostCategoryOrderByCreatedDateDesc(postCategory, page);
        return posts.stream().
                map(Post::convertToViewDTO).
                collect(Collectors.toList());
    }

    @Transactional
    public PostDTO save(PostDTO postDTO, PostCategory postCategory) {
        Post post = Post.builder()
                .user(postDTO.getUser())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .createdDate(PostDTO.getNow())
                .postCategory(postCategory)
                //.files()       //file부분 바꿀거있어서 대기
                .view(0)
                .build();
        postRepository.save(post);
        return postDTO;
    }

    public PostDTO findPostById(Long postId){
        Post post = postRepository.findById(postId).get();
        return post.convertToDTO();
    }

    public PostNickNameDTO findNickNameById(Long postId){
        Post post = postRepository.findById(postId).get();
        return post.convertToNickName();
    }

    @Transactional
    public void viewIncrease(Long postId){
        Post post = postRepository.findById(postId).get();
        post.viewIncrease();
    }

    @Transactional
    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post post = postRepository.findById(postId).get();
        Post updatePost = post.update(postDTO.getTitle(), postDTO.getContent());
        return updatePost.convertToDTO();
    }

    @Transactional
    public void DeletePost(Long postId){
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }

}

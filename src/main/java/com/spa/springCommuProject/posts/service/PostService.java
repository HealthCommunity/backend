package com.spa.springCommuProject.posts.service;


import com.spa.springCommuProject.file.service.FileService;
import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.*;
import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final UserRepository userRepository;
    private final FileService fileService;

    public Page<PostViewDTO> findPagingPosts(PostCategory postCategory, Pageable page) {
        Page<Post> pagingPosts = postRepository.findByPostCategory(postCategory, page);
        return pagingPosts.map(Post::convertToViewDTO);
    }

    public Page<PostViewDTO> findAllPostsByUserId(Long userId, Pageable pageable) {
        User findUser = userRepository.findById(userId).get();
        Page<Post> pagingPosts = postRepository.findByUserOrderByCreatedDateDesc(findUser, pageable);
        return pagingPosts.map(Post::convertToViewDTO);
    }


    @Transactional
    public PostResponse save(PostRequest postRequest, PostCategory postCategory) {
        User user = userRepository.findById(postRequest.getUserId()).orElseThrow();
        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postCategory(postCategory)
                .build();
        postRepository.save(post);
        List<String> urls = fileService.saveFiles(postRequest.getFiles(), post);

        return new PostResponse(post, user.getNickName(), urls);
    }

    @Transactional
    public ThreePostResponse threeSave(ThreePostRequest threePostRequest, PostCategory postCategory) {
        User user = userRepository.findById(threePostRequest.getUserId()).orElseThrow();
        Post post = Post.builder()
                .user(user)
                .title(threePostRequest.getTitle())
                .content(threePostRequest.getContent())
                .postCategory(postCategory)
                .build();
        postRepository.save(post);
        String benchUrl = fileService.save(threePostRequest.getBench(), post);
        String squatUrl = fileService.save(threePostRequest.getSquat(), post);
        String deadUrl = fileService.save(threePostRequest.getDead(), post);

        return new ThreePostResponse(post, user.getNickName(), benchUrl, squatUrl, deadUrl);
    }

    @Transactional
    public List<PostViewDTO> searchTitle(String keyword) {
        return postRepository
                .findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(Post::convertToViewDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostViewDTO> searchTitleAndContent(String keyword) {
        return postRepository
                .findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(Post::convertToViewDTO)
                .collect(Collectors.toList());
    }

    public PostDTO findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return post.convertToDTO();
    }

    public PostNickNameDTO findNickNameById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return post.convertToNickNameDTO();
    }

    @Transactional
    public void viewIncrease(Long postId) {
        Post post = postRepository.findById(postId).get();
        post.viewIncrease();
    }

    @Transactional
    public PostDTO updatePost(Long postId, PostDTO postDTO) {
        Post post = postRepository.findById(postId).get();
        Post updatePost = post.update(postDTO.getTitle(), postDTO.getContent()); //나중에 file추가
        return updatePost.convertToDTO();
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }

}

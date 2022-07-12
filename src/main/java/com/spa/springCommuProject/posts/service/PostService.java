package com.spa.springCommuProject.posts.service;


import com.spa.springCommuProject.file.domain.FileCategory;
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
    public PostDTO save(PostDTO postDTO, PostCategory postCategory) {
        User user = userRepository.findById(postDTO.getUserId()).orElseThrow();
        Post post = Post.builder()
                .user(user)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .postCategory(postCategory)
                //.files()       //file부분 바꿀거있어서 대기
                .build();
        postRepository.save(post);
        return postDTO;
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
        String benchUrl = fileService.save(threePostRequest.getBench(), post, FileCategory.VIDEO);
        String squatUrl = fileService.save(threePostRequest.getSquat(), post, FileCategory.VIDEO);
        String deadUrl = fileService.save(threePostRequest.getDead(), post, FileCategory.VIDEO);
        postRepository.save(post);
        ThreePostResponse threePostResponse = new ThreePostResponse(post, user.getNickName(), benchUrl, squatUrl, deadUrl);
        return threePostResponse;
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

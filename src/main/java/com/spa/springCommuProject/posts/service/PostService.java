package com.spa.springCommuProject.posts.service;


import com.spa.springCommuProject.config.login.PrincipalUserDetails;
import com.spa.springCommuProject.file.domain.FileDetail;
import com.spa.springCommuProject.file.domain.VideoCategory;
import com.spa.springCommuProject.file.service.FileService;
import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.*;
import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    public List<PostViewDTO> findPagingPosts(PostCategory postCategory, Integer page, Integer size) {
        PageRequest pageRequest =PageRequest.of(page, size, Sort.by("id").descending());
        List<Post> pagingPosts = postRepository.findByPostCategory(postCategory, pageRequest);
        return pagingPosts.stream().map(Post::convertToViewDTO).collect(Collectors.toList());
    }

    public List<PostViewDTO> findAllPostsByUserId(Long userId, Pageable pageable) {
        User findUser = userRepository.findById(userId).get();
        List<Post> pagingPosts = postRepository.findByUserOrderByCreatedDateDesc(findUser, pageable);
        return pagingPosts.stream().map(Post::convertToViewDTO).collect(Collectors.toList());
    }


    @Transactional
    public PostResponse save(PostRequest postRequest, PostCategory postCategory, PrincipalUserDetails principalUserDetails) {
        List<String> urls = new ArrayList<>();
        User user = principalUserDetails.getUser();
        Post post = Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .postCategory(postCategory)
                .build();
        postRepository.save(post);
        if(!postRequest.getFiles().isEmpty()) {
            urls = fileService.saveFiles(postRequest.getFiles(), post);
        }

        return new PostResponse(post, urls);
    }

    @Transactional
    public ThreePostResponse threeSave(ThreePostRequest threePostRequest, PostCategory postCategory,PrincipalUserDetails principalUserDetails) {
        User user = principalUserDetails.getUser();
        Post post = Post.builder()
                .user(user)
                .title(threePostRequest.getTitle())
                .content(threePostRequest.getContent())
                .postCategory(postCategory)
                .build();
        postRepository.save(post);
        String benchUrl = fileService.saveFile(threePostRequest.getBench(), post, VideoCategory.BENCH);
        String squatUrl = fileService.saveFile(threePostRequest.getSquat(), post, VideoCategory.SQUAT);
        String deadUrl = fileService.saveFile(threePostRequest.getDead(), post, VideoCategory.DEAD);

        return new ThreePostResponse(post, benchUrl, squatUrl, deadUrl);
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

    public PostResponse findPostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        List<FileDetail> files = post.getFiles();
        List<String> urls = files.stream().map(x -> x.getUrl()).collect(Collectors.toList());
        return new PostResponse(post, urls);
    }

    public PostNickNameDTO findNickNameById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return post.convertToNickNameDTO();
    }

    public MainPageDTO mainPagePosts() {
        MainPageDTO mainPageDTO = new MainPageDTO();
        PageRequest pageRequest =PageRequest.of(0, 30, Sort.by("createdDate").descending());
        mainPageDTO.setFreeposts(postRepository.findByPostCategory(PostCategory.FREEPOST, pageRequest).stream().
                map(Post::convertToMainPostResponse)
                .collect(Collectors.toList()));
        mainPageDTO.setExerciseposts(postRepository.findByPostCategory(PostCategory.EXERCISEPOST, pageRequest).stream().
                map(Post::convertToMainPostResponse)
                .collect(Collectors.toList()));
        mainPageDTO.setThreepowerposts(postRepository.findByPostCategory(PostCategory.THREEPOWERPOST, pageRequest).stream().
                map(Post::convertToMainPostResponse)
                .collect(Collectors.toList()));
        pageRequest = PageRequest.of(0, 10);
        mainPageDTO.setUsers(userRepository.findByAvailableOrderByBigThreePowerSumDesc(true, pageRequest).stream()
                .map(User::convertToMainUserResponse).collect(Collectors.toList()));
        return mainPageDTO;
    }

    @Transactional
    public void viewIncrease(Long postId) {
        Post post = postRepository.findById(postId).get();
        post.viewIncrease();
    }

    @Transactional
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        Post post = postRepository.findById(postId).get();
        Post updatePost = post.update(postRequest.getTitle(), postRequest.getContent());
        List<String> urls = fileService.updateFiles(postRequest.getFiles(), updatePost);
        return new PostResponse(updatePost, urls);
    }

    @Transactional
    public ThreePostResponse threeUpdatePost(Long postId, ThreePostRequest threePostRequest) {
        Post post = postRepository.findById(postId).get();
        Post updatePost = post.update(threePostRequest.getTitle(), threePostRequest.getContent());
        String benchUrl = fileService.updateFile(threePostRequest.getBench(), updatePost);
        String squatUrl = fileService.updateFile(threePostRequest.getSquat(), updatePost);
        String deadUrl = fileService.updateFile(threePostRequest.getDead(), updatePost);
        return new ThreePostResponse(updatePost, benchUrl, squatUrl, deadUrl);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }

}

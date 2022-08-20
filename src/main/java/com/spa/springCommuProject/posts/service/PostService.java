package com.spa.springCommuProject.posts.service;


import com.spa.springCommuProject.common.exception.NoThreePowerException;
import com.spa.springCommuProject.config.login.PrincipalUserDetails;
import com.spa.springCommuProject.file.domain.FileDetail;
import com.spa.springCommuProject.file.domain.VideoCategory;
import com.spa.springCommuProject.file.service.FileService;
import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.*;
import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.user.domain.Role;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.dto.SessionUserResponse;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    public List<PostViewDTO> findPagingPosts(PostCategory postCategory, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        List<Post> pagingPosts = postRepository.findByPostCategory(postCategory, pageRequest);
        List<PostViewDTO> posts = pagingPosts.stream().map(Post::convertToViewDTO).collect(Collectors.toList());
        setPostsUrls(posts);
        return posts;
    }

    private void setPostsUrls(List<PostViewDTO> posts) {
        for (PostViewDTO post : posts) {
            List<FileDetail> files = postRepository.findById(post.getPostId()).get().getFiles();
            if (files.size() == 0) {
                ArrayList<String> urls = new ArrayList<>();
                urls.add("https://healthcommunitybucket.s3.ap-northeast-2.amazonaws.com/IMAGE/d6afdfee-401b-418d-a925-9f1b84387a31.png");
                post.setUrls(urls);
            } else {
                List<String> urls = files.stream().map(x -> x.getUrl()).collect(Collectors.toList());
                post.setUrls(urls);
            }
        }
    }

    public List<PostViewDTO> findAllPostsByUserId(PrincipalUserDetails principalUserDetails, Pageable pageable) {
        User findUser = principalUserDetails.getUser();
        List<Post> pagingPosts = postRepository.findByUserOrderByCreatedDateDesc(findUser, pageable);
        List<PostViewDTO> posts = pagingPosts.stream().map(Post::convertToViewDTO).collect(Collectors.toList());
        setPostsUrls(posts);
        return posts;
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
        if (postRequest.getFiles() != null) {
            urls = fileService.saveFiles(postRequest.getFiles(), post);
        }

        return new PostResponse(post, urls);
    }

    @Transactional
    public ThreePostResponse threeSave(ThreePostRequest threePostRequest, PostCategory postCategory, PrincipalUserDetails principalUserDetails) throws NoThreePowerException {
        User user = principalUserDetails.getUser();
        //User user = userRepository.getById(1L);
        if (threePostRequest.getBench().getOriginalFilename().equals("") || threePostRequest.getDead().getOriginalFilename().equals("")
                || threePostRequest.getSquat().getOriginalFilename().equals("")) {
            throw new NoThreePowerException("123");
        }
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

    public List<PostViewDTO> searchByTitle(String keyword) {
        List<PostViewDTO> posts = postRepository
                .findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(Post::convertToViewDTO)
                .collect(Collectors.toList());
        setPostsUrls(posts);

        return posts;
    }

    public List<PostViewDTO> searchByTitleAndContent(String keyword) {
        List<PostViewDTO> posts = postRepository
                .findByContentContainingIgnoreCaseOrTitleContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(Post::convertToViewDTO)
                .collect(Collectors.toList());
        setPostsUrls(posts);

        return posts;
    }

    public List<PostViewDTO> searchByContent(String keyword) {
        List<PostViewDTO> posts = postRepository
                .findByContentContainingIgnoreCase(keyword)
                .stream()
                .map(Post::convertToViewDTO)
                .collect(Collectors.toList());
        setPostsUrls(posts);

        return posts;
    }

    public List<PostViewDTO> searchByUser(String keyword) {
        User findUser = userRepository.findByNickName(keyword);
        List<PostViewDTO> posts = postRepository
                .findByUser(findUser)
                .stream()
                .map(Post::convertToViewDTO)
                .collect(Collectors.toList());
        setPostsUrls(posts);

        return posts;
    }

    public PostResponse findPostById(Long postId, PrincipalUserDetails principalUserDetails) {
        Post post = postRepository.findById(postId).get();
        List<FileDetail> files = post.getFiles();
        List<String> urls = files.stream().map(x -> x.getUrl()).collect(Collectors.toList());
        SessionUserResponse sessionUserResponse = authUser(principalUserDetails);
        return new PostResponse(post, urls, sessionUserResponse);
    }

    private SessionUserResponse authUser(PrincipalUserDetails principalUserDetails) {
        SessionUserResponse sessionUserResponse;
        if (principalUserDetails == null) {
            sessionUserResponse = new SessionUserResponse(0L, Role.ROLE_USER);
        } else {
            sessionUserResponse = new SessionUserResponse(principalUserDetails.getUser().getId(), principalUserDetails.getUser().getRole());
        }
        return sessionUserResponse;
    }

    public PostUpdateResponse findUpdatePostById(Long postId) {
        Post post = postRepository.findById(postId).get();
        List<FileDetail> files = post.getFiles();
        List<String> urls = files.stream().map(x -> x.getUrl()).collect(Collectors.toList());
        return new PostUpdateResponse(post, urls);
    }

    public PostNickNameDTO findNickNameById(Long postId) {
        Post post = postRepository.findById(postId).get();
        return post.convertToNickNameDTO();
    }

    public MainPageDTO mainPagePosts() {
        MainPageDTO mainPageDTO = new MainPageDTO();
        PageRequest pageRequest = PageRequest.of(0, 30, Sort.by("createdDate").descending());
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
        String benchUrl = fileService.updateFile(threePostRequest.getBench(), updatePost, VideoCategory.BENCH);
        String squatUrl = fileService.updateFile(threePostRequest.getSquat(), updatePost, VideoCategory.SQUAT);
        String deadUrl = fileService.updateFile(threePostRequest.getDead(), updatePost, VideoCategory.DEAD);
        return new ThreePostResponse(updatePost, benchUrl, squatUrl, deadUrl);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }

    public void validateUser(Long postId, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElseThrow(IllegalAccessError::new);

        Long postUserId = post.getUser().getId();
        if (!postUserId.equals(userId)) {
            throw new IllegalArgumentException("해당 게시글 권한이 없습니다.");
        }
    }

}

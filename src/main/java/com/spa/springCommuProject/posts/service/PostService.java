package com.spa.springCommuProject.posts.service;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostDTO> findAllPosts(PostCategory postCategory) {
        List<Post> posts = postRepository.findByPostCategory(postCategory);
        return posts.stream().
                map(Post::convertToDTO).
                collect(Collectors.toList());
    }

    public int PostsCount(PostCategory postCategory) {
        return postRepository.findByPostCategory(postCategory).size();
    }

    public Page<Post> findPagingPosts(PostCategory postCategory, Pageable page) {
        Page<Post> posts = postRepository.findByPostCategoryOrderByCreatedDateDesc(postCategory, page);
        return posts;
    }

    public Page<PostViewDTO> findPagingPostsAndCount(PostCategory postCategory, Pageable page){
//        Map<String, Object> map = new HashMap<>();
        Page<Post> pagingPosts = findPagingPosts(postCategory, page);
//        int count =postRepository.findByPostCategory(postCategory).size(); //자유게시판 총 개수 프런트에 넘겨줘야 페이지 개수 만들수 있지 않을까
//
//        map.put("freePostCount", count);
//        map.put("freePosts", pagingPosts);
        return pagingPosts.map(Post::convertToViewDTO);
    }

//    public Page<PostDTO> findAllPostsByUserId(Long userId, Pageable pageable) {
//        //User user = userRepository.findById(userId).get();
//        Page<Post> posts = postRepository.findsByUserOrderByCreatedDateDesc(userId, pageable);
//        return posts.map(Post::convertToDTO);
//    }


    @Transactional
    public PostDTO save(PostDTO postDTO, PostCategory postCategory) {
        User user = userRepository.findById(postDTO.getUserId()).orElseThrow();
        Post post = Post.builder()
            .user(user)
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
        return post.convertToNickNameDTO();
    }

    @Transactional
    public void viewIncrease(Long postId){
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
    public void deletePost(Long postId){
        Post post = postRepository.findById(postId).get();
        postRepository.delete(post);
    }

}

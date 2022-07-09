package com.spa.springCommuProject.posts.service;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.dto.PostNickNameDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
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

    public Page<PostViewDTO> findPagingPosts(PostCategory postCategory, Pageable page){
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

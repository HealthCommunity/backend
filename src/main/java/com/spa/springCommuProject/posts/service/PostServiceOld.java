//package com.spa.springCommuProject.posts.service;
//
//
//import com.spa.springCommuProject.posts.domain.FreePost;
//import com.spa.springCommuProject.posts.domain.PhotoPost;
//import com.spa.springCommuProject.posts.domain.Post;
//import com.spa.springCommuProject.posts.domain.VideoPost;
//import com.spa.springCommuProject.posts.repository.PostRepositoryOld;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class PostServiceOld {
//
//    private final PostRepositoryOld postRepositoryOld;
//
//    @Transactional
//    public Long savePost(Post post){
//        postRepositoryOld.save(post);
//        return post.getId();
//    }
//
//    public Post findOnePost(Long postId){
//        return postRepositoryOld.findOnePost(postId);
//    }
//
//    public List<Post> findAvailablePosts(){
//        return postRepositoryOld.findAvailableAll();
//    }
//
//
//
//    public List<FreePost> findAvailableFreePosts(){
//        return postRepositoryOld.findAvailableAllFreePosts();
//    }
//
//    public List<FreePost> findAvailablePagingFreePosts(int page){
//        return postRepositoryOld.findAvailablePagingFreePosts(page);
//    }
//
//    public List<FreePost> mainPageFreePosts(){
//        return postRepositoryOld.mainPageFreePosts();
//    }
//
//    public List<PhotoPost> findAvailablePhotoPosts(){
//        return postRepositoryOld.findAvailableAllPhotoPost();
//    }
//
//    public List<PhotoPost> findAvailableAllPhotoPostViewDesc(){
//        return postRepositoryOld.findAvailableAllPhotoPostViewDesc();
//    }
//
//    public List<PhotoPost> findAvailablePagingPhotoPosts(int page){
//        return postRepositoryOld.findAvailablePagingPhotoPosts(page);
//    }
//
//    public List<VideoPost> findAvailableVideoPosts(){
//        return postRepositoryOld.findAvailableAllVideoPost();
//    }
//
//    public List<VideoPost> findAvailablePagingVideoPosts(int page){
//        return postRepositoryOld.findAvailablePagingVideoPosts(page);
//    }
//
//    public List<VideoPost> mainPageVideoPosts(){
//        return postRepositoryOld.mainPageVideoPosts();
//    }
//
//    public List<Post> findAllPosts(){
//        return postRepositoryOld.findAll();
//    }
//
//    @Transactional
//    public void updatePost(Long postId, String title, String content) {
//        Post findPost = postRepositoryOld.findOnePost(postId);
//        findPost.update(title,content);
//    }
//
//    @Transactional
//    public void deletePost(Long postId) {
//        Post findPost = postRepositoryOld.findOnePost(postId);
//        findPost.delete();
//    }
//
//    @Transactional
//    public void viewIncrease(Long postId){
//        Post findPost = postRepositoryOld.findOnePost(postId);
//        findPost.viewIncrease();
//    }
//}

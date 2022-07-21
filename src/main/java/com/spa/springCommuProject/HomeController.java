//package com.spa.springCommuProject;
//
//
//import com.spa.springCommuProject.posts.domain.Post;
//import com.spa.springCommuProject.posts.repository.PostRepository;
//import com.spa.springCommuProject.user.domain.User;
//import com.spa.springCommuProject.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
//public class HomeController {
//
//    private final UserRepository userRepository;
//    private final PostRepository postRepository;
//
//
//
//    //public String home(){
//    @GetMapping("/")
//    public String home(){
//        return "home";
//    }
//
//    @GetMapping("/userall")
//    public List<User> userall(){
//        return userRepository.findAll();
//    }
//
//    @GetMapping("/postall")
//    public List<Post> postall(){
//        List<Post> all = postRepository.findAll();
//        return all;
//    }
//}

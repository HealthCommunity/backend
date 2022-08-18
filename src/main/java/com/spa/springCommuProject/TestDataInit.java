package com.spa.springCommuProject;


import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Transactional
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @PostConstruct
    public void createDummyData() {
//        String password = bCryptPasswordEncoder.encode("1234");
//        String password2 = bCryptPasswordEncoder.encode("1234");
//
//        User june = new User("june", "june", password);
//        User hoon = new User("hoon", "hoon", password);
//        User tail = new User("tail", "tail", password2);
//        User song = new User("song", "song", password2);
//
//        userRepository.save(june);
//        userRepository.save(hoon);
//        userRepository.save(tail);
//        userRepository.save(song);
//        ArrayList<Post> posts = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            posts.add(new Post(june, "title" + i, "content" + i, PostCategory.EXERCISEPOST));
//            posts.add(new Post(hoon, "title" + i, "content" + i, PostCategory.FREEPOST));
//            posts.add(new Post(tail, "title" + i, "content" + i, PostCategory.THREEPOWERPOST));
//            posts.add(new Post(song, "title" + i, "content" + i, PostCategory.EXERCISEPOST));
//        }
//        postRepository.saveAll(posts);
    }


}

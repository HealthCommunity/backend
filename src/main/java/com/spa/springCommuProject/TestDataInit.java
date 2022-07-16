package com.spa.springCommuProject;


import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@Transactional
@RequiredArgsConstructor
public class TestDataInit {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostConstruct
    public void createDummyData() {
        User june = new User("june", "june", "1234");
        User hoon = new User("hoon", "hoon", "1234");
        User tail = new User("tail", "tail", "1234");
        User song = new User("song", "song", "1234");

        userRepository.save(june);
        userRepository.save(hoon);
        userRepository.save(tail);
        userRepository.save(song);
//        ArrayList<Post> posts = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            posts.add(new Post(june, "title" + i, "content" + i, PostCategory.EXERCISEPOST));
//            posts.add(new Post(hoon, "title" + i, "content" + i, PostCategory.FREEPOST));
//            posts.add(new Post(tail, "title" + i, "content" + i, PostCategory.THREEPOWERPOST));
//            posts.add(new Post(song, "title" + i, "content" + i, PostCategory.EXERCISEPOST));
//        }
//        postRepository.saveAll(posts);
//    }

    }
}

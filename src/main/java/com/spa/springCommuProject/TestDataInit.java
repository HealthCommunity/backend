package com.spa.springCommuProject;


import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.posts.domain.PostCategory;
import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.posts.repository.PostRepository;
import com.spa.springCommuProject.posts.service.PostService;
import com.spa.springCommuProject.user.domain.BigThreePower;
import com.spa.springCommuProject.user.domain.Role;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.dto.UserJoinDTO;
import com.spa.springCommuProject.user.repository.UserRepository;
import com.spa.springCommuProject.user.service.UserService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        String password = bCryptPasswordEncoder.encode("1234");

        User june = new User("june", "june", password);
        User hoon = new User("hoon", "hoon", password);
        User tail = new User("tail", "tail", password);
        User song = new User("song", "song", password);

        userRepository.save(june);
        userRepository.save(hoon);
        userRepository.save(tail);
        userRepository.save(song);
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            posts.add(new Post(june, "제목" + i, "글" + i, PostCategory.EXERCISEPOST));
            posts.add(new Post(hoon, "제목" + i, "글" + i, PostCategory.FREEPOST));
            posts.add(new Post(tail, "제목" + i, "글" + i, PostCategory.THREEPOWERPOST));
            posts.add(new Post(song, "제목" + i, "글" + i, PostCategory.EXERCISEPOST));
        }
        postRepository.saveAll(posts);
    }

}

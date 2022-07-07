package com.spa.springCommuProject.user.repository;

import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.user.domain.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

//@Repository
@RequiredArgsConstructor
public class UserRepositoryOld {


    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findById(Long id){
        return em.find(User.class, id);
    }



    public List<User> findAll(){
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public List<User> findByNickName(String nickName){
        return em.createQuery("select u from User u where u.nickName = :nickName",User.class)
                .setParameter("nickName",nickName)
                .getResultList();
    }

    public Optional<User> findByLoginId(String loginId){
        return findAll().stream().filter(m->m.getLoginId().equals(loginId)).findFirst();
    }

    public List<User> findUsersSumDesc(){
        return em.createQuery("select u from User u where u.available = :available order by u.bigThreePower.sum desc", User.class)
                .setParameter("available", true)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();
    }


    public List<Post> findAllPostsByUser(User user){
        return em.createQuery("select p from Post p where p.user = :user order by p.createdDate desc", Post.class)
                .setParameter("user", user)
                .getResultList(); // 나중에 페이징 필요
    }
}

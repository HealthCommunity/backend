package com.spa.springCommuProject.user.repository;

import com.spa.springCommuProject.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByNickName(String nickName);

    public Optional<User> findByLoginId(String loginId);

    //@Query("select u from User u join fetch u.p")
    public List<User> findByAvailableOrderByBigThreePowerSumDesc(boolean available, Pageable pageable);

}

package com.spa.springCommuProject.user.service;

import com.spa.springCommuProject.user.domain.BigThreePower;
import com.spa.springCommuProject.user.domain.Role;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.dto.*;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        validateDuplicateUser(user); //중복 닉네임, 아이디 검증
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public UserJoinDTO join(UserJoinDTO userJoinDTO) {
        User user = User.builder()
                .nickName(userJoinDTO.getNickName())
                .password(userJoinDTO.getPassword())
                .loginId(userJoinDTO.getLoginId())
                .bigThreePower(new BigThreePower(0, 0, 0))
                .role(Role.USER)
                .build();

        validateDuplicateUser(user); //중복 닉네임, 아이디 검증
        userRepository.save(user);
        return userJoinDTO;
    }

    private void validateDuplicateUser(User user) {
        List<User> findUserByNickName = userRepository.findByNickName(user.getNickName());
        if (!findUserByNickName.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
        if (userRepository.findByLoginId(user.getLoginId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    public UserIdDTO findLoginIdById(Long userId) {
        User findUser = userRepository.findById(userId).get();
        UserIdDTO userIdDto = new UserIdDTO(findUser.getLoginId());
        return userIdDto;
    }

    public UserPageDTO findPageById(Long userId) {
        User findUser = userRepository.findById(userId).get();
        UserPageDTO userPageDto = new UserPageDTO(findUser.getLoginId(), findUser.getNickName(), findUser.getBigThreePower());
        return userPageDto;
    }

    public UserUpdateDTO findUpdateById(Long userId) {
        User findUser = userRepository.findById(userId).get();
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(findUser.getNickName(), findUser.getPassword());
        return userUpdateDTO;
    }



    public UserLoginDTO login(UserLoginDTO userLoginDTO) {
        /**
         * return null 이면 로그인 실패
         */
        User user = userRepository.findByLoginId(userLoginDTO.getLoginId())
                .filter(m -> m.getPassword().equals(userLoginDTO.getPassword()))
                .orElseThrow();
        return new UserLoginDTO(user.getLoginId(), user.getPassword());
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        User findUser = userRepository.findById(userId).get();
        findUser.update(userUpdateDTO.getNickName(), userUpdateDTO.getPassword());
    }

    @Transactional
    public void deleteUser(Long userId) {
        User findUser = userRepository.findById(userId).get();
        findUser.delete();
    }

    @Transactional
    public void updateBigThree(Long userId, BigThreePower bigThreePower) {
        User findUser = userRepository.findById(userId).get();
        findUser.updateBig(bigThreePower);
    }
}

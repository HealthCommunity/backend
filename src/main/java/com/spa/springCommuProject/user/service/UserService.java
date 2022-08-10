package com.spa.springCommuProject.user.service;

import com.spa.springCommuProject.config.login.PrincipalUserDetails;
import com.spa.springCommuProject.user.domain.BigThreePower;
import com.spa.springCommuProject.user.domain.Provider;
import com.spa.springCommuProject.user.domain.Role;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.dto.*;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserJoinResponse join(UserJoinRequest userJoinRequest) {
        User user = User.builder()
            .loginId(userJoinRequest.getLoginId())
            .nickName(userJoinRequest.getNickName())
            .password(bCryptPasswordEncoder.encode(userJoinRequest.getPassword()))
            .provider(Provider.SELF)
            .bigThreePower(new BigThreePower(0, 0, 0))
            .role(Role.USER)
            .available(true)
            .build();

        validateDuplicateUser(userJoinRequest); //중복 닉네임, 아이디 검증
        userRepository.save(user);
        return new UserJoinResponse(user.getId(), user.getNickName());
    }

    private void validateDuplicateUser(UserJoinRequest userJoinRequest) {
        List<User> findUserByNickName = userRepository.findByNickName(userJoinRequest.getNickName());
        if (!findUserByNickName.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
        if (!userJoinRequest.getPasswordCheck().equals(userJoinRequest.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다");
        }
        if (userRepository.findByLoginId(userJoinRequest.getLoginId()).isPresent()) {
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
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO(findUser.getLoginId(), findUser.getNickName(), findUser.getPassword());
        return userUpdateDTO;
    }



    public UserLoginDTO login(UserLoginDTO userLoginDTO) {
        /**
         * return null 이면 로그인 실패
         */
        User user = userRepository.findByLoginId(userLoginDTO.getUsername())
                .filter(m -> m.getPassword().equals(userLoginDTO.getPassword()))
                .orElseThrow();
        return new UserLoginDTO(user.getLoginId(), user.getPassword());
    }

    @Transactional
    public void updateUser(PrincipalUserDetails principalUserDetails, UserUpdateDTO userUpdateDTO) {
        User findUser = principalUserDetails.getUser();
        User user = userRepository.findById(findUser.getId()).get();
        String changePassword = bCryptPasswordEncoder.encode(userUpdateDTO.getPassword());
        user.update(userUpdateDTO.getNickName(), changePassword);
    }

    @Transactional
    public void deleteUser(PrincipalUserDetails principalUserDetails) {
        User findUser = principalUserDetails.getUser();
        findUser.delete();
    }

    @Transactional
    public void updateBigThree(PrincipalUserDetails principalUserDetails, BigThreeDTO bigThreeDTO) {
        User findUser = principalUserDetails.getUser();
        BigThreePower bigThreePower = new BigThreePower(bigThreeDTO.getSquat(), bigThreeDTO.getBench(), bigThreeDTO.getDead());
        findUser.updateBig(bigThreePower);
    }
}

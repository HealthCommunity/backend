package com.spa.springCommuProject.config.login;

import com.spa.springCommuProject.common.exception.LoginException;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> optionalUser = userRepository.findByLoginId(username);
        if (optionalUser.isPresent()) {
            User loginUser = optionalUser.get();
            if (!loginUser.getAvailable()) {
                throw new LoginException("회원탈퇴된 계정입니다.");
            }
            return new PrincipalUserDetails(loginUser);
        }
        throw new LoginException("해당 회원이 존재하지 않습니다.");
    }
}

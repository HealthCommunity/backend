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
        return optionalUser.map(PrincipalUserDetails::new).orElseThrow(()->new LoginException("로그인 에러"));
    }
}

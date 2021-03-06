package com.spa.springCommuProject.config.login;

import com.spa.springCommuProject.config.login.provider.FacebookUserInfo;
import com.spa.springCommuProject.config.login.provider.GoogleUserInfo;
import com.spa.springCommuProject.config.login.provider.NaverUserInfo;
import com.spa.springCommuProject.config.login.provider.OAuth2UserInfo;
import com.spa.springCommuProject.user.domain.BigThreePower;
import com.spa.springCommuProject.user.domain.Role;
import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {




        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();
        String loginId = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();
        Optional<User> optionalUser = userRepository.findByLoginId(loginId);

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            // ????????? ?????? ??????
            user = User.builder()
                .loginId(loginId)
                .nickName(username)
                .email(email)
                .provider(provider)
                .providerId(providerId)
                .role(Role.USER)
                .bigThreePower(new BigThreePower(0, 0, 0))
                .available(true)
                .build();
            userRepository.save(user);
        }

        return new PrincipalUserDetails(user, oAuth2User.getAttributes());
    }

}

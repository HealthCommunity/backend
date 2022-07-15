package com.spa.springCommuProject.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private boolean postOnly = true;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }


        ObjectMapper mapper = new ObjectMapper();

        LoginDto loginDto = mapper.readValue(request.getInputStream(), LoginDto.class); // getInputStream 에러 잡기 -> SneakyThrows

        System.out.println("userLoginDto.getUsername() = " + loginDto.getUsername());
        System.out.println("userLoginDto.getPassword() = " + loginDto.getPassword());

        String username = loginDto.getUsername();
        username = (username != null) ? username : "";
        username = username.trim();
        String password = loginDto.getPassword();
        password = (password != null) ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}

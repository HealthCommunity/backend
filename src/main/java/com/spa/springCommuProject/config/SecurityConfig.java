package com.spa.springCommuProject.config;

import com.spa.springCommuProject.config.login.JsonUsernamePasswordAuthenticationFilter;
import com.spa.springCommuProject.config.login.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/swagger-ui.html/**").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/join").permitAll()
                .antMatchers("/api/exercisepost/list").permitAll()
                .antMatchers("/api/exercisepost/{postId}").permitAll()
                .antMatchers("/api/freepost/list").permitAll()
                .antMatchers("/api/freepost/{postId}").permitAll()
                .antMatchers("/api/threepowerpost/list").permitAll()
                .antMatchers("/api/threepowerpost/{postId}").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(getJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        http.headers()
                .frameOptions()
                .sameOrigin();
        http.logout().
                logoutUrl("/api/user/logout").
                deleteCookies("JSESSIONID");
    }

    @Bean
    protected JsonUsernamePasswordAuthenticationFilter getJsonUsernamePasswordAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/api/user/login");
        return filter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOrigin("http://54.166.132.169:3200");
        corsConfiguration.addAllowedOrigin("http://localhost:3200");
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}

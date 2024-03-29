package com.spa.springCommuProject.config;

import com.spa.springCommuProject.config.login.CustomAuthFailureHandler;
import com.spa.springCommuProject.config.login.CustomAuthSuccessHandler;
import com.spa.springCommuProject.config.login.CustomAuthSuccessHandler2;
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

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Autowired
    private CustomAuthFailureHandler customAuthFailureHandler;

    @Autowired
    private CustomAuthSuccessHandler customAuthSuccessHandler;

    @Autowired
    private CustomAuthSuccessHandler2 customAuthSuccessHandler2;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .and().ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
                        "/swagger-resources", "/configuration/security", "/swagger-resources/**",
                        "/swagger-ui.html", "/webjars/**", "/swagger/**", "/swagger-ui.html/**", "/webjars/**", "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/api/user/{userId}/bigthreepower").hasRole("MASTER")
                .antMatchers("/").permitAll()
                .antMatchers("/api").permitAll()
                .antMatchers("/api/search").permitAll()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/api/user/join").permitAll()
                .antMatchers("/api/exercisepost").permitAll()
                .antMatchers("/api/exercisepost/popular").permitAll()
                .antMatchers("/api/exercisepost/{postId}").permitAll()
                .antMatchers("/api/freepost").permitAll()
                .antMatchers("/api/freepost/{postId}").permitAll()
                .antMatchers("/api/threepowerpost").permitAll()
                .antMatchers("/api/threepowerpost/{postId}").permitAll()
                .antMatchers("/api/post/{id}/comments").permitAll()
                .antMatchers("/api/post/{postId}/comments").permitAll()
                .antMatchers("/api/comment/{commentId}/delete").permitAll()
                .antMatchers("/api/comment/{commentId}/edit").permitAll()
                .anyRequest().authenticated()
                ;
        http.addFilterBefore(getJsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
                .and()
                .successHandler(customAuthSuccessHandler2)
                .failureHandler(customAuthFailureHandler);
        http.headers()
                .frameOptions()
                .sameOrigin();
        http.logout(logout -> logout
                .permitAll()
                .logoutUrl("/api/user/logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                }));
    }

    @Bean
    protected JsonUsernamePasswordAuthenticationFilter getJsonUsernamePasswordAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(customAuthFailureHandler);
        filter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
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

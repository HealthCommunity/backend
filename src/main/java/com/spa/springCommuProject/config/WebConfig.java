package com.spa.springCommuProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080",
                        "http://54.166.132.169:8080",
                        "https://54.166.132.169:8080",
                        "http://localhost:3000",
                        "http://127.0.0.1:3000",
                        "https://localhost:3000",
                        "https://127.0.0.1:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE")
        ;

    }

}
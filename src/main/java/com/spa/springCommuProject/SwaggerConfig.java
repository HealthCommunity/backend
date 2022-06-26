package com.spa.springCommuProject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("HealthCommunity")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("HealthCommunity")
                .description("HealthCommunity API Test for Swagger Documentation")
                .termsOfServiceUrl("https://wangtak.tistory.com")
                .license("WangTak")
                .licenseUrl("https://wangtak.tistory.com").version("1.0").build();
    }
}

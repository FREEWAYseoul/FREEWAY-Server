package team.free.freeway.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("freewaykr.s3-website.ap-northeast-2.amazonaws.com")
                .allowedOrigins("http://freewaykr.s3-website.ap-northeast-2.amazonaws.com")
                .allowedOrigins("https://freewaykr.s3-website.ap-northeast-2.amazonaws.com")
                .allowedOrigins("localhost:3000")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET");
    }
}

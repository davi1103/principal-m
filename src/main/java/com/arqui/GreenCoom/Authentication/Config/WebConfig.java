package com.arqui.GreenCoom.Authentication.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*/@Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
               .allowedOrigins("http://localhost:8001")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
               .exposedHeaders("Authorization", "Link", "X-Total-Count")
                .maxAge(3600);
    }*/
}
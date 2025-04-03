package com.winterhold.library_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/author").setViewName("forward:/author/index");
        registry.addViewController("/category").setViewName("forward:/category/index");
        registry.addViewController("/book").setViewName("forward:/book/index");
    }
}

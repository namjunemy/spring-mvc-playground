package io.namjune.springmvcplayground.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreetingInterceptor()).order(-10);
        registry.addInterceptor(new AnotherInterceptor())
            .order(10)
            .addPathPatterns("/hello/user");
    }
}

package io.namjune.springmvcplayground.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreetingInterceptor()).order(-10);
        registry.addInterceptor(new AnotherInterceptor())
            .order(10)
            .addPathPatterns("/hello/user");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mobile/**")
            .addResourceLocations("classpath:/mobile/")
            // 캐시 전략 설정
            .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
    }
}

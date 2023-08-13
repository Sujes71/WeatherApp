package com.springbootapp.weatherapp.config;

import com.springbootapp.weatherapp.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/aemet/**")
                .addPathPatterns("/api/aemet/**")
                .addPathPatterns("/forecast/**")
                .addPathPatterns("/api/forecast/**")
                .addPathPatterns("/command/**")
                .addPathPatterns("/api/command/**")
                .order(1);
    }
}
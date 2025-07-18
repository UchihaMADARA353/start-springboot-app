package com.example.start_spring_app.config;

import com.example.start_spring_app.interceptor.RegisterFlowInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final RegisterFlowInterceptor registerFlowInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(registerFlowInterceptor)
                .addPathPatterns("/api/v1/auth/register/token");
    }
}

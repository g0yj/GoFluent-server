package com.lms.api.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final LoginInfoArgumentResolver loginInfoArgumentResolver;
  private final LoginAdminInterceptor loginAdminInterceptor;
  private final LoginMobileInterceptor loginMobileInterceptor;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowedOrigins("http://localhost", "http://localhost:5173", "http://localhost:8080",
                    "http://127.0.0.1", "http://127.0.0.1:5173",
                    "http://englishchannel.co.kr", "http://englishchannel.co.kr:5173",
                    "http://www.englishchannel.co.kr", "http://www.englishchannel.co.kr:5173")
            .allowCredentials(true);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginInfoArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginAdminInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns("/**/login");

//    registry.addInterceptor(loginMobileInterceptor)
//        .addPathPatterns("/mobile/**")
//        .excludePathPatterns("/**/login");
  }
}

package com.bigwork.bigwork_meta.common;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class SaTokenConfigure implements WebMvcConfigurer {
  // 注册拦截器
  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
    log.info("Sa-Token 拦截器注册成功");
    registry.addInterceptor(new SaInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/user/login")
        .excludePathPatterns("/user/register")
        .excludePathPatterns("/swagger-ui.html", "/v2/api-docs", "/swagger-resources/**", "/webjars/**");

  }
}

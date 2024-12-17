# meta

## 1.登录注册模块

​	采用Spring Security进行权限管理

### 	Spring Security

##### 	用户名密码登录

![image-20241217171419937](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20241217171419937.png)

![image-20241217171453108](C:\Users\Admin\AppData\Roaming\Typora\typora-user-images\image-20241217171453108.png)

可以实现无论用户访问什么页面，没权限都回跳回登录界面



配置SecurityConfig

```
package com.bigwork.bigwork_meta.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeRequests(authorizeRequests ->
            authorizeRequests
                .antMatchers("/login").permitAll()//访问login不需要授权
                .anyRequest().authenticated()//访问别的内容需要授权
        )
        .formLogin(form ->
            form
                .loginPage("/login")//如果发现未授权，配置请求路径，朝着这个url请求
                .permitAll()
        );

    return http.build();
  }

}
```



当请求login过来的时候，返回一个login会被spring boot自动解析成login.html的网页

```
package com.bigwork.bigwork_meta.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {
  @GetMapping("/login")
  String login() {
    return "login";
  }
}

```

重定向后展示的html登录页面

```
<!DOCTYPE html>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
<head>
    <title>Please Log In</title>
</head>
<body>
<h1>Please Log In</h1>
<div th:if="${param.error}">
    Invalid username and password.</div>
<div th:if="${param.logout}">
    You have been logged out.</div>
<form th:action="@{/login}" method="post">
    <div>
        <input type="text" name="username" placeholder="Username"/>
    </div>
    <div>
        <input type="password" name="password" placeholder="Password"/>
    </div>
    <input type="submit" value="Log in" />
</form>
</body>
</html>
```









### 	验证码相关

​	[ele-admin/EasyCaptcha: Java图形验证码，支持gif、中文、算术等类型，可用于Java Web、JavaSE等项目。](https://github.com/ele-admin/EasyCaptcha)



​		

### 	多点登录实现

​	未定，再说
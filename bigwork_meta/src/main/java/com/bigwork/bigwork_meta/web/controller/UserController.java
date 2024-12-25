package com.bigwork.bigwork_meta.web.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.bigwork.bigwork_meta.service.UserService;
import com.bigwork.bigwork_meta.web.model.LoginReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {
  @Resource private UserService userService;
  @PostMapping("/login")
  @ApiOperation(value = "登录")

  public Result<String> login(@RequestBody LoginReq req) {
    userService.login(req);

    return Result.buildSuccess("登录成功");
  }
  @PostMapping("/register")
  @ApiOperation(value = "注册")

  public Result<String> register(@RequestBody LoginReq req) {
    userService.register(req);
    return Result.buildSuccess("注册成功");
  }

  @RequestMapping("/doLogin")
  public String doLogin(String username, String password) {
    // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
    if("zhang".equals(username) && "123456".equals(password)) {
      StpUtil.login(10001);
      return "登录成功";
    }
    return "登录失败";
  }

  // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
  @RequestMapping("/isLogin")
  public String isLogin() {
    return "当前会话是否登录：" + StpUtil.isLogin();
  }

}

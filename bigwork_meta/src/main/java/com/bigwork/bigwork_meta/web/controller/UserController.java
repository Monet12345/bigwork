package com.bigwork.bigwork_meta.web.controller;



import com.bigwork.bigwork_meta.service.UserService;
import com.bigwork.bigwork_meta.web.model.CaptchaVo;
import com.bigwork.bigwork_meta.web.model.LoginReq;

import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/captcha")
  @ApiOperation(value = "获取验证码")
  public Result<CaptchaVo> captcha(){
    return Result.buildSuccess(userService.getCaptcha());
  }




}

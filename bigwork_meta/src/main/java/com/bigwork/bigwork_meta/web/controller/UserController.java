package com.bigwork.bigwork_meta.web.controller;



import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.bigwork.bigwork_meta.service.UserService;
import com.bigwork.bigwork_meta.web.model.CaptchaVo;
import com.bigwork.bigwork_meta.web.model.LoginReq;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;



@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")

public class UserController {
  @Resource private UserService userService;
  @PostMapping("/login")
  @ApiOperation(value = "登录")
  public Result<SaTokenInfo> login(@RequestBody LoginReq req) {

    return Result.buildSuccess(userService.login(req));
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

  @GetMapping("/logout")
  @ApiOperation(value = "退出登录")
  public Result<String> logout(){
    if(StpUtil.isLogin()){
      StpUtil.logout();
      return Result.buildSuccess("退出成功");
    }
    else{
      return Result.buildFailure("本来就未登录");
    }


  }

  @GetMapping("/login/github")
  @ApiOperation(value = "使用github登录，请求这个接口会返回给前端一个url，前端需要请求这个url来获取用户信息")
  public Result<String> loginByGithub() throws IOException, InterruptedException {

    return Result.buildSuccess("https://github.com/login/oauth/authorize?client_id=Ov23lilFmwrnT9298kxG&redirect_uri=http://localhost:8082/meta/user/login/github/callback");

  }

  //请求github授权后的回调接口，github会携带code来调这边，这边再去请求拿到access_token
  @GetMapping("/login/github/callback")
  @ApiOperation(value = "github回调")
  public Result loginByGithubCallback(@RequestParam String code) throws IOException, InterruptedException {

    userService.githubCallBack(code);
    return Result.buildSuccess();

  }



}

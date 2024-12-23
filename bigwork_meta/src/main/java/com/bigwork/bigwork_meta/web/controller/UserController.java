package com.bigwork.bigwork_meta.web.controller;


import com.bigwork.bigwork_meta.web.model.LoginReq;
import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@ApiOperation(value = "用户相关接口")
public class UserController {
  @PostMapping
  public Result<String> login(@RequestBody LoginReq req) {


    return Result.buildSuccess("登录成功");
  }

}

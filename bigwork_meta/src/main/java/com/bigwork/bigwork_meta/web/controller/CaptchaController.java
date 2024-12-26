package com.bigwork.bigwork_meta.web.controller;

import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Api(tags = "验证码相关接口")
public class CaptchaController {

  @RequestMapping("/captcha")
  @ApiOperation(value = "获取验证码")
  public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
    CaptchaUtil.out(request, response);
  }

}
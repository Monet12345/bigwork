package com.bigwork.bigwork_meta.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.bigwork.bigwork_meta.web.model.CaptchaVo;
import com.bigwork.bigwork_meta.web.model.LoginReq;

public interface UserService {
  SaTokenInfo login(LoginReq req);//登录，返回token
  void register(LoginReq req);
  CaptchaVo getCaptcha();
  void loginByGithub();
}

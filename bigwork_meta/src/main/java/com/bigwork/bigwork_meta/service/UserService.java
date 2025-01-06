package com.bigwork.bigwork_meta.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.bigwork.bigwork_meta.model.CaptchaVo;
import com.bigwork.bigwork_meta.model.LoginReq;
import com.bigwork.bigwork_meta.model.UserToken;

import java.io.IOException;

public interface UserService {
  SaTokenInfo login(LoginReq req);//登录，返回token
  void register(LoginReq req);
  CaptchaVo getCaptcha();
  UserToken githubCallBack(String code, String workspaceId) throws IOException, InterruptedException;
}

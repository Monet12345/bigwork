package com.bigwork.bigwork_meta.service;

import com.bigwork.bigwork_meta.web.model.LoginReq;

public interface UserService {
  void login(LoginReq req);
  void register(LoginReq req);

}

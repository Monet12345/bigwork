package com.bigwork.bigwork_meta.service.Impl;


import Util.BizException;
import cn.dev33.satoken.stp.StpUtil;
import com.bigwork.bigwork_meta.dal.mapper.UserMapper;
import com.bigwork.bigwork_meta.dal.modle.UserDo;
import com.bigwork.bigwork_meta.service.UserService;
import com.bigwork.bigwork_meta.web.model.LoginReq;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserServiceImpl implements UserService {
  @Resource
  private UserMapper userMapper;

  @Override
  public boolean login(LoginReq req) {
    if(StpUtil.isLogin()){
      throw new BizException("已登录");
    }
    UserDo userDo = userMapper.selectByUserName(req.getUsername(),req.getWorkspaceId());
    if(userDo == null||!checkPassword(userDo)){
      throw new BizException("用户名或密码错误");
    }

    StpUtil.setLoginId(userDo.getUserId());






    return false;
  }

  boolean checkPassword(UserDo userDo){
    return true;



    //加盐密码校验业务
  }

  void creatPassword( ){

  }
}

package com.bigwork.bigwork_meta.service.Impl;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.bigwork.bigwork_meta.dal.mapper.UserMapper;
import com.bigwork.bigwork_meta.dal.modle.UserDo;
import com.bigwork.bigwork_meta.service.IdManagementService;
import com.bigwork.bigwork_meta.service.UserService;
import com.bigwork.bigwork_meta.web.model.LoginReq;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.DecoderException;
import org.springframework.stereotype.Component;
import util.BizException;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


import static cn.hutool.core.date.DateUtil.now;

@Component

public class UserServiceImpl implements UserService {
  @Resource
  private UserMapper userMapper;
  @Resource private IdManagementService idManagementService;

  private static final int SALT_LENGTH = 16; // 盐的长度

  @Override

  public void login(LoginReq req) {
    if (StpUtil.isLogin()) {
      throw new BizException("已登录");
    }
    UserDo userDo = userMapper.selectByUserName(req.getUserName(), req.getWorkspaceId());
    if (userDo == null || !checkPassword(userDo, req.getPassword())) {
      throw new BizException("用户名或密码错误");
    }

    StpUtil.login(userDo.getUserId());
  }

  @Override
  public void register(LoginReq req) {
    UserDo row = userMapper.selectByUserName(req.getUserName(), req.getWorkspaceId());
    if(row!=null){
      throw new BizException("用户已存在");
    }
    UserDo userDo = BeanUtil.copyProperties(req, UserDo.class);
    userDo.setCreateTime(now());
    userDo.setUpdateTime(now());
    userDo = creatPassword(userDo);
    userDo.setWorkspaceId(req.getWorkspaceId());
    userDo.setNickName("未定义昵称（请自行修改昵称）");
    userMapper.add(userDo);
  }

  boolean checkPassword(UserDo userDo, String password) {
    String[] parts = userDo.getPassword().split("\\$");//盐和密码以$分割存在数据库当中
    if (parts.length != 2) {
      return false;
    }
    String salt = parts[0];
    String hashedPassword = parts[1];
    String hashedInputPassword = hashPassword(password, salt);
    return hashedPassword.equals(hashedInputPassword);
  }

  UserDo creatPassword(UserDo userDo) {
    String salt = generateSalt();
    String hashedPassword = hashPassword(userDo.getPassword(), salt);
    userDo.setPassword(salt + "$" + hashedPassword);
    return userDo;
  }

  private String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[SALT_LENGTH];
    random.nextBytes(salt);
    return Hex.encodeHexString(salt);
  }

  private String hashPassword(String password, String salt) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(Hex.decodeHex(salt.toCharArray()));
      byte[] hashedBytes = md.digest(password.getBytes());
      return Hex.encodeHexString(hashedBytes);
    } catch (NoSuchAlgorithmException | DecoderException | org.apache.commons.codec.DecoderException e) {
      throw new RuntimeException("Error hashing password", e);
    }
  }
}

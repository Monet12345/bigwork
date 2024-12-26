package com.bigwork.bigwork_meta.web.model;


import lombok.Data;

@Data
public class LoginReq {
  private String userName;
  private String password;
  private String workspaceId;
  private String verCode;//验证码
  private String verKey;//当前验证码对应的uuid，用于从redis找到对应的值
}

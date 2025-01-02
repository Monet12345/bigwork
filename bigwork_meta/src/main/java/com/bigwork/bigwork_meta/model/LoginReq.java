package com.bigwork.bigwork_meta.model;


import lombok.Data;

@Data
public class LoginReq {
  private String userName;
  private String password;
  private String workspaceId;
  private String verCode;//验证码
  private String verKey;//当前验证码对应的uuid，用于从redis找到对应的值
  private String nickName;//非必填字段，不填写就是默认名称
}

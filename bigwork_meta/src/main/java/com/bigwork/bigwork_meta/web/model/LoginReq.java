package com.bigwork.bigwork_meta.web.model;


import lombok.Data;

@Data
public class LoginReq {
  private String userName;
  private String password;
  private String workspaceId;
}

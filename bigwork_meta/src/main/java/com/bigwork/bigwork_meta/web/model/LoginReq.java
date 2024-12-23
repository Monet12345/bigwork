package com.bigwork.bigwork_meta.web.model;


import lombok.Data;

@Data
public class LoginReq {
  private String username;
  private String password;
  private String workspaceId;
}

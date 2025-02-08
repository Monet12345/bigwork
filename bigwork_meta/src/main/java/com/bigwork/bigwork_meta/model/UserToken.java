package com.bigwork.bigwork_meta.model;

import lombok.Data;

@Data
public class UserToken {
  private String token;
  private String tokenName;
  private String expiresIn;//过期时间
  private String userId;
}

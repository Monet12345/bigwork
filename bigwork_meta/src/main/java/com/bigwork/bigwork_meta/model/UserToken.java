package com.bigwork.bigwork_meta.model;

import lombok.Data;

@Data
public class UserToken {
  String token;
  String tokenName;
  String expiresIn;//过期时间
  String userId;
}

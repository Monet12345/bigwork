package com.bigwork.bigwork_meta.dal.modle;

import lombok.Data;

@Data
public class UserDo {
  private String userId;//Id
  private String userName;//账号
  private String nickName;//昵称
  private String password;//密码
  private String salt;
  private String workspaceId;
  private String createTime;
  private String updateTime;
}

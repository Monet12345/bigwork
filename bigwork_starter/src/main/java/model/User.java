package model;

import lombok.Data;

@Data
public class User {
  private String userId;//Id
  private String userName;//账号
  private String nickName;//昵称
  private String password;//密码
  private String workspaceId;
}

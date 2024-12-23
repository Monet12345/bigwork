package model;

import lombok.Data;

@Data
public class User {
  private String userId;//Id
  private String username;//账号
  private String nickname;//昵称
  private String password;//密码
  private String workSpaceId;
}

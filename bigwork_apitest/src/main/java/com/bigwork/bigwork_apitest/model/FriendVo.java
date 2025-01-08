package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class FriendVo {
  public enum Status {
    TEXT("text"),
    IMAGE("image"),
    FILE("file");
    private String value;
    Status(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }
  }
  private String userId;
  private String workspaceId;
  private String friendId;
  private Long unread;
  private String status;

}

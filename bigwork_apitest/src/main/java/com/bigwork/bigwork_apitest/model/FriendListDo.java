package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class FriendListDo {
  private String userId;
  private String workspaceId;
  private String friendId;
  private Long unread;
  private String status;
  private String gmtUpdate;
}

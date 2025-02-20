package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class FriendListVo {
    private Long id;
    private String sendType;
    private String userId;
    private String userNickname;
    private String workspaceId;
    private String friendId;
    private String friendNickname;
    private String content;
    private Long unread;
    private String status;
    private String gmtUpdate;
}

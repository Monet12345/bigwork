package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class ChatDetailVo {
    private Long id;
    private String userId;
    private String friendId;
    private String contentUserNickname;
    private String content;
    private String gmtUpdate;
    private String type;
    private String workspaceId;
}

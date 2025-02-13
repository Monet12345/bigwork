package com.bigwork.bigwork_apitest.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChatDetailReq {
    private String userId;
    private String friendId;
    private String workspaceId;
    @ApiModelProperty(value = "当前已经展示的聊天记录数量")
    private int realOffset;
    @ApiModelProperty(value = "想要的聊天记录数量")
    private int pageSize;
}

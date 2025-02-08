package com.bigwork.bigwork_apitest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import model.PageForm;

@Data
@ApiModel("请求好友列表")
public class FriendListReq extends PageForm {
    private String userId;
    private String workspaceId;
    @ApiModelProperty(value = "当前已经展示的列表数量")
    private String realOffset;
}

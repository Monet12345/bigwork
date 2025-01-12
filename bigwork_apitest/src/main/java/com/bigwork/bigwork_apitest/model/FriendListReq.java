package com.bigwork.bigwork_apitest.model;

import lombok.Data;
import model.PageForm;

@Data
public class FriendListReq extends PageForm {
    private String userId;
    private String workspaceId;

}

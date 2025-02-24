package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class ResourceIterationDo {
    private String resourceDataId;
    private String iteration;
    private String parent;//父iteration
    private String workspaceId;
    private String updateUserId;
    private String gmtCreate;
    private String gmtUpdate;
}

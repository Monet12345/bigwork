package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class ResourceDo {
    private String resourceDataId;
    private String name;
    private String data;
    private String createUserId;
    private String updateUserId;
    private String gmtCreate;
    private String gmtUpdate;
    private String workspaceId;
    private int iteration;
    private int exist;
}

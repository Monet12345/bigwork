package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class ResourceReq {
    private String resourceDataId;
    private String name;
    private String data;
    private String updateUserId;
    private String workspaceId;
    private int iteration;
}

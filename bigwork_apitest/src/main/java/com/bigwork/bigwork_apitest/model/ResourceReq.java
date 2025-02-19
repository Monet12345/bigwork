package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class ResourceReq {
    private String resourceDateId;
    private String name;
    private String data;
    private String updateUserId;
    private String workspaceId;
}

package com.bigwork.bigwork_apitest.model;

import lombok.Data;

@Data
public class ResourceDo {
    private String resourceDateId;
    private String name;
    private String data;
    private String createUserId;
    private String updateUserId;
    private String gmtCreate;
    private String gmtUpdate;
    private String workspaceId;
    private String iteration;
    private String exist;
}

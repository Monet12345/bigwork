package com.bigwork.bigwork_apitest.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class ResourceReq {
    private String resourceDataId;
    private String name;
    private String data;
    private String updateUserId;
    private String workspaceId;
    private String iteration;
}

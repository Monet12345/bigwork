package com.bigwork.bigwork_apitest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import model.PageForm;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchListReq extends PageForm {
    private String name;
    private String workspaceId;
}

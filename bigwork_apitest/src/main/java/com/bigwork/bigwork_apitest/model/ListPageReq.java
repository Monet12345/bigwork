package com.bigwork.bigwork_apitest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import model.PageForm;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListPageReq extends PageForm {
    private String workspaceId;

}

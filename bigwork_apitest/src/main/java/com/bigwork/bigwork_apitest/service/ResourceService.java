package com.bigwork.bigwork_apitest.service;

import com.bigwork.bigwork_apitest.model.ListPageReq;
import com.bigwork.bigwork_apitest.model.ResourceReq;

import com.bigwork.bigwork_apitest.model.VagueResourceVo;
import model.Page;
import model.PageForm;

public interface ResourceService {
    void createList(ResourceReq resourceReq);
    void deleteList(String resourceDateId,String workspaceId);

    Page<VagueResourceVo> getListPage(ListPageReq listPageReq);
}

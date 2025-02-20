package com.bigwork.bigwork_apitest.service;

import com.bigwork.bigwork_apitest.model.*;

import model.Page;
import model.PageForm;

public interface ResourceService {
    void createList(ResourceReq resourceReq);
    void deleteList(String resourceDateId,String workspaceId);

    Page<VagueResourceVo> getListPage(ListPageReq listPageReq);
    ResourceVo getListDetail(String resourceDateId,String workspaceId);

    Page<VagueResourceVo> searchList(SearchListReq searchListReq);

    void addListMessage(ResourceReq resourceReq);

}

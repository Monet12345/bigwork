package com.bigwork.bigwork_apitest.service;

import com.bigwork.bigwork_apitest.model.*;

import model.Page;
import model.PageForm;

public interface ResourceService {
    void createList(ResourceReq resourceReq);
    void deleteList(String resourceDataId,String workspaceId,int iteration);

    Page<VagueResourceVo> getListPage(ListPageReq listPageReq);
    ResourceVo getListDetail(String resourceDataId,String workspaceId);

    Page<VagueResourceVo> searchList(SearchListReq searchListReq);

    void updateListMessage(ResourceReq resourceReq);

}

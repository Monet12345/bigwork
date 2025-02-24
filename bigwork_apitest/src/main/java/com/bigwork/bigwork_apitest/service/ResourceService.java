package com.bigwork.bigwork_apitest.service;

import com.bigwork.bigwork_apitest.model.*;

import model.Page;
import model.PageForm;

import java.util.ArrayList;
import java.util.List;

public interface ResourceService {
    void submitList(ResourceReq resourceReq);
    void deleteList(String resourceDataId,String workspaceId,String iteration);

    Page<VagueResourceVo> getListPage(ListPageReq listPageReq);
    ResourceVo getListDetail(String resourceDataId,String iteration,String workspaceId);



    List<List<String>>getIterationTree(String resourceDataId,String workspaceId);



}

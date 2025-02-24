package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.common.aop.ResourceDbInterface;
import com.bigwork.bigwork_apitest.model.ListPageReq;
import com.bigwork.bigwork_apitest.model.ResourceDo;
import com.bigwork.bigwork_apitest.model.SearchListReq;
import com.bigwork.bigwork_apitest.model.VagueResourceVo;
import model.PageForm;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper

public interface ResourceDataMapper {
    @ResourceDbInterface(type = "write")
    void createList(ResourceDo resourceDo);
    @ResourceDbInterface(type = "write")
    void deleteList(String resourceDataId,String iteration,String workspaceId);
    @ResourceDbInterface(type = "write")
    void updateList (ResourceDo resourceDo);
    @ResourceDbInterface(type = "read")
    List<VagueResourceVo> getListInPage(ListPageReq listPageReq);
    @ResourceDbInterface(type = "read")
    Long getListInPageCount(String workspaceId);
    @ResourceDbInterface(type = "read")
    ResourceDo getListDetailById(String resourceDataId,String iteration,String workspaceId);



}

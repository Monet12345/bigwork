package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.common.aop.ResourceDbInterface;
import com.bigwork.bigwork_apitest.model.ListPageReq;
import com.bigwork.bigwork_apitest.model.ResourceDo;
import model.PageForm;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper

public interface ResourceDataMapper {
    @ResourceDbInterface(type = "write")
    void createList(ResourceDo resourceDo);
    @ResourceDbInterface(type = "write")
    void deleteList(String resourceDateId,String workspaceId);
    @ResourceDbInterface(type = "read")
    List<ResourceDo> getListInPage(ListPageReq listPageReq);
    @ResourceDbInterface(type = "read")
    Long getListInPageCount(String workspaceId);

}

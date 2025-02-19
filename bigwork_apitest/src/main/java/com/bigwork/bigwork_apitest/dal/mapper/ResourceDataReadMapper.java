package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.model.ListPageReq;
import com.bigwork.bigwork_apitest.model.ResourceDo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ResourceDataReadMapper {

    List<ResourceDo> getListInPage(ListPageReq listPageReq);

    Long getListInPageCount(String workspaceId);

}

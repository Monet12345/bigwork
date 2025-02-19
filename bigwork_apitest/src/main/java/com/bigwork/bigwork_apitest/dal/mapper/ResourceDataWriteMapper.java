package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.model.ListPageReq;
import com.bigwork.bigwork_apitest.model.ResourceDo;
import model.PageForm;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ResourceDataWriteMapper {
    void createList(ResourceDo resourceDo);
    void deleteList(String resourceDateId,String workspaceId);



}

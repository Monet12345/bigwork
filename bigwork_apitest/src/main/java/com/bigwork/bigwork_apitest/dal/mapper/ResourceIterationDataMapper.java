package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.common.aop.ResourceDbInterface;
import com.bigwork.bigwork_apitest.model.ResourceDo;
import com.bigwork.bigwork_apitest.model.ResourceIterationDo;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ResourceIterationDataMapper {
    @ResourceDbInterface(type = "write")
    void add(@Param(value = "resourceIterationDo") ResourceIterationDo resourceIterationDo);

    @ResourceDbInterface(type = "write")
    void deleteById(String resourceDataId,String iteration,String workspaceId);

    //更新iteration节点的parent字段值
    @ResourceDbInterface(type = "write")
    void updateParent(String resourceDataId,String iteration,String parent,String workspaceId);

    @ResourceDbInterface(type = "read")
    ResourceIterationDo getDetailById(String resourceDataId,String iteration,String workspaceId);

    @ResourceDbInterface(type = "read")
    List<ResourceIterationDo> getAll();



}

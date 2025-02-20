package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.common.aop.ResourceDbInterface;
import com.bigwork.bigwork_apitest.model.ResourceDo;
import com.bigwork.bigwork_apitest.model.ResourceIterationDo;
import org.mapstruct.Mapper;

@Mapper
public interface ResourceIterationDataMapper {
    @ResourceDbInterface(type = "write")
    void createIteration(ResourceIterationDo resourceIterationDo);
    @ResourceDbInterface(type = "write")
    int update(ResourceIterationDo resourceIterationDo);

    @ResourceDbInterface(type = "read")
    ResourceIterationDo getIterationById(String resourceDateId);



}

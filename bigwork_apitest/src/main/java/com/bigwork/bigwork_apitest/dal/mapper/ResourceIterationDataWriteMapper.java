package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.model.ResourceDo;
import com.bigwork.bigwork_apitest.model.ResourceIterationDo;
import org.mapstruct.Mapper;

@Mapper
public interface ResourceIterationDataWriteMapper {
    void createIteration(ResourceIterationDo resourceIterationDo);

}

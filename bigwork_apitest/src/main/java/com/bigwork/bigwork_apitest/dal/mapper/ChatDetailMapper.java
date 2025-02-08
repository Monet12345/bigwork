package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.model.ChatDetailDo;
import org.mapstruct.Mapper;

@Mapper
public interface ChatDetailMapper {
    void add(ChatDetailDo chatDetailDo);

}

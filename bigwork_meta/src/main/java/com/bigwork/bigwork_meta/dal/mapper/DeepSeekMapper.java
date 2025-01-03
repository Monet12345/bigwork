package com.bigwork.bigwork_meta.dal.mapper;

import com.bigwork.bigwork_meta.dal.modle.DeepSeekDo;
import com.bigwork.bigwork_meta.model.DeepSeekMessage;
import org.mapstruct.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DeepSeekMapper {
    int update(@Param("message")String message,@Param("workspaceId")String workspaceId);
    DeepSeekDo getMessage(@Param("workspaceId")String workspaceId);
    void insert(@Param("deepSeekDo")DeepSeekDo deepSeekDo);
}

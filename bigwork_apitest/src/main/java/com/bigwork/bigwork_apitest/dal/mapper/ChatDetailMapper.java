package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.model.ChatDetailDo;
import com.bigwork.bigwork_apitest.model.ChatDetailReq;
import com.bigwork.bigwork_apitest.model.FriendListDo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ChatDetailMapper {
    void add(ChatDetailDo chatDetailDo);
    List<ChatDetailDo> getChatDetailByRealOffset(ChatDetailReq chatDetailReq);
}

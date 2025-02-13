package com.bigwork.bigwork_apitest.service;

import com.bigwork.bigwork_apitest.model.ChatDetailDo;
import com.bigwork.bigwork_apitest.model.ChatDetailReq;
import com.bigwork.bigwork_apitest.model.FriendListReq;
import com.bigwork.bigwork_apitest.model.FriendVo;
import model.Page;

import java.util.List;


public interface ChatService {
  Page<FriendVo> getFriendList(FriendListReq friendListReq);
  List<ChatDetailDo> getChatDetail(ChatDetailReq chatDetailReq);
}

package com.bigwork.bigwork_apitest.service;

import com.bigwork.bigwork_apitest.model.*;
import model.Page;

import java.util.List;


public interface ChatService {
  Page<FriendVo> getFriendList(FriendListReq friendListReq);
  List<ChatDetailVo> getChatDetail(ChatDetailReq chatDetailReq);
}

package com.bigwork.bigwork_apitest.service;

import com.bigwork.bigwork_apitest.model.FriendListReq;
import com.bigwork.bigwork_apitest.model.FriendVo;
import model.Page;


public interface ChatService {
  Page<FriendVo> getFriendList(FriendListReq friendListReq);
}

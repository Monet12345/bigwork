package com.bigwork.bigwork_apitest.service.Impl;


import cn.hutool.core.bean.BeanUtil;
import com.bigwork.bigwork_apitest.dal.mapper.UserChatMapper;
import com.bigwork.bigwork_apitest.model.FriendListDo;
import com.bigwork.bigwork_apitest.model.FriendListReq;
import com.bigwork.bigwork_apitest.model.FriendVo;
import com.bigwork.bigwork_apitest.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import model.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChatServiceImpl implements ChatService {
  @Resource
  private UserChatMapper userChatMapper;
  @Override
  public Page<FriendVo> getFriendList(FriendListReq friendListReq) {
    List<FriendListDo> friendList = userChatMapper.getFriendList(friendListReq);
    List<FriendVo> friendVos = new ArrayList<>();
    friendList.stream().forEach(friendListDo -> {
      FriendVo friendVo = BeanUtil.copyProperties(friendListDo, FriendVo.class);
      friendVos.add(friendVo);
    });
    return  new Page<>(friendListReq.getPageNo(), friendListReq.getPageSize(), (long) friendVos.size(), friendVos);
  }
}

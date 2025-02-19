package com.bigwork.bigwork_apitest.service.Impl;


import api.UserFacade;
import cn.hutool.core.bean.BeanUtil;
import com.bigwork.bigwork_apitest.dal.mapper.ChatDetailMapper;
import com.bigwork.bigwork_apitest.dal.mapper.UserChatMapper;
import com.bigwork.bigwork_apitest.model.*;
import com.bigwork.bigwork_apitest.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import model.ClientMapSingleton;
import model.Page;
import model.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChatServiceImpl implements ChatService {
  @Resource
  private UserChatMapper userChatMapper;
  @Resource
  private ChatDetailMapper chatDetailMapper;
  @Resource
  private UserFacade userFacade;
  @Override
  public Page<FriendVo> getFriendList(FriendListReq friendListReq) {
    List<FriendListDo> friendList = userChatMapper.getFriendList(friendListReq);
    List<FriendVo> friendVos = new ArrayList<>();
    friendList.stream().forEach(friendListDo -> {
      FriendVo friendVo = BeanUtil.copyProperties(friendListDo, FriendVo.class);
      User user = userFacade.getUserById(friendListDo.getUserId(), friendListDo.getWorkspaceId()).getData();
      friendVo.setUserNickname(user.getNickName());
      User friend = userFacade.getUserById(friendListDo.getFriendId(), friendListDo.getWorkspaceId()).getData();
      friendVo.setFriendNickname(friend.getNickName());
      if(ClientMapSingleton.getInstance().getClient(friendListDo.getUserId())==null){
        friendVo.setStatus("离线");
      }
      else {
        friendVo.setStatus("在线");
      }
      friendVos.add(friendVo);
    });
    return  new Page<>(friendListReq.getPageNo(), friendListReq.getPageSize(), (long) friendVos.size(), friendVos);
  }

  @Override
  public List<ChatDetailVo> getChatDetail(ChatDetailReq chatDetailReq) {

    List<ChatDetailDo> chatDetailDos = chatDetailMapper.getChatDetailByRealOffset(chatDetailReq);
    List<ChatDetailVo> chatDetailVos = chatDetailDos.stream().map(chatDetailDo -> {
      ChatDetailVo chatDetailVo = BeanUtil.copyProperties(chatDetailDo, ChatDetailVo.class);
      User user = userFacade.getUserById(chatDetailDo.getUserId(), chatDetailDo.getWorkspaceId()).getData();
      chatDetailVo.setContentUserNickname(user.getNickName());
      return chatDetailVo;
    }).toList();
return chatDetailVos;
}

}

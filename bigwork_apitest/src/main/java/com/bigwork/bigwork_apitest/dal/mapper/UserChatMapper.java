package com.bigwork.bigwork_apitest.dal.mapper;

import com.bigwork.bigwork_apitest.model.FriendListDo;
import com.bigwork.bigwork_apitest.model.FriendListReq;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserChatMapper {
  List<FriendListDo>getFriendList(FriendListReq friendListReq);
  FriendListDo getFriendListByUserIdAndFriendId( String userId,String friendId,String workspaceId);
  void updateFriendList(FriendListDo friendListDo);


}

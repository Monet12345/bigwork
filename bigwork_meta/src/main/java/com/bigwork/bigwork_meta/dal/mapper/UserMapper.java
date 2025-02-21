package com.bigwork.bigwork_meta.dal.mapper;

import com.bigwork.bigwork_meta.dal.modle.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
  int add(@Param("userDo")UserDo userDo);
  UserDo selectByUserName(@Param("userName") String userName,@Param("workspaceId") String workspaceId);

  UserDo selectByUserId(@Param("userId") String userId,@Param("workspaceId") String workspaceId);

  List<UserDo> getUserByWorkspaceId(@Param("workspaceId") String workspaceId);
}

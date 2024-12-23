package com.bigwork.bigwork_meta.dal.mapper;

import com.bigwork.bigwork_meta.dal.modle.UserDo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  UserDo add(UserDo userDo);
  UserDo selectByUserName(String userName,String workspaceId);

}

package com.bigwork.bigwork_meta.web.facade;

import api.UserFacade;
import cn.hutool.core.bean.BeanUtil;
import com.bigwork.bigwork_meta.dal.mapper.UserMapper;
import com.bigwork.bigwork_meta.dal.modle.UserDo;
import io.swagger.annotations.Api;
import model.Result;
import model.User;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "获取用户信息相关facade")
public class UserFacadeController implements UserFacade {
    @Resource private UserMapper userMapper;
    @Override
    public Result<User> getUserById(String userId,String workspaceId) {
        UserDo userDo = userMapper.selectByUserId(userId,workspaceId);
        User user = BeanUtil.copyProperties(userDo,User.class);
        return Result.buildSuccess(user);
    }
}

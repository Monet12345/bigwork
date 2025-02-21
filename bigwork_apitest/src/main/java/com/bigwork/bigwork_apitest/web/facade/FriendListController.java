package com.bigwork.bigwork_apitest.web.facade;

import api.FriendListFacade;
import api.UserFacade;
import com.bigwork.bigwork_apitest.common.KafkaConsumerFact;
import com.bigwork.bigwork_apitest.dal.mapper.UserChatMapper;
import com.bigwork.bigwork_apitest.model.FriendListDo;
import io.swagger.annotations.Api;
import model.Result;
import model.User;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.hutool.core.date.DateUtil.now;

@RestController
@Api(tags ="聊天模块用户列表相关facade")
public class FriendListController implements FriendListFacade {
    @Resource
    private UserChatMapper userChatMapper;
    @Resource
    private UserFacade userFacade;


    @Override
    public Result<String> setChatFriends(String userId, String workspaceId) {
        List<User> users = userFacade.getUserByWorkspaceId(workspaceId).getData();
        users.forEach(user -> {
            FriendListDo friendListDo = userChatMapper.getFriendListByUserIdAndFriendId(userId,user.getUserId(),workspaceId);
            if (friendListDo==null){
                FriendListDo friendListDo1 = new FriendListDo();
                friendListDo1.setUserId(userId);
                friendListDo1.setFriendId(user.getUserId());
                friendListDo1.setWorkspaceId(workspaceId);
                friendListDo1.setStatus("0");
                friendListDo1.setUnread(0L);
                friendListDo1.setGmtUpdate(now());
                userChatMapper.insertFriendList(friendListDo1);
            }
        });
        users.forEach(user -> {
            FriendListDo friendListDo = userChatMapper.getFriendListByUserIdAndFriendId(user.getUserId(),userId,workspaceId);
            if (friendListDo==null){
                FriendListDo friendListDo1 = new FriendListDo();
                friendListDo1.setUserId(user.getUserId());
                friendListDo1.setFriendId(userId);
                friendListDo1.setWorkspaceId(workspaceId);
                friendListDo1.setStatus("0");
                friendListDo1.setUnread(0L);
                friendListDo1.setGmtUpdate(now());
                userChatMapper.insertFriendList(friendListDo1);
            }
        });


        return Result.buildSuccess();
    }
}

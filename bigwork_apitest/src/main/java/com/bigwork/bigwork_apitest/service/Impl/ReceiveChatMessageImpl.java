package com.bigwork.bigwork_apitest.service.Impl;

import com.bigwork.bigwork_apitest.dal.mapper.ChatDetailMapper;
import com.bigwork.bigwork_apitest.dal.mapper.UserChatMapper;
import com.bigwork.bigwork_apitest.model.ChatDetailDo;
import com.bigwork.bigwork_apitest.model.FriendListDo;
import com.bigwork.bigwork_apitest.model.NewChatDetail;
import com.bigwork.bigwork_apitest.service.ReceiveChatMessage;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import model.ClientMapSingleton;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import util.JsonSerializer;

import javax.annotation.Resource;

import static cn.hutool.core.date.DateUtil.now;

@Component
@Slf4j
public class ReceiveChatMessageImpl implements ReceiveChatMessage {

    @Resource
    private ChatDetailMapper chatDetailMapper;
    @Resource
    private UserChatMapper userChatMapper;
    @Override
    public void receiveChatDetailDo(String message) {
        ChatDetailDo chatDetailDo= JsonSerializer.deserializeFromJson(message,ChatDetailDo.class);





        //将接收的消息存入数据库当中，让接收方这边可以显示聊天记录内容
        String tmp=chatDetailDo.getUserId();
        chatDetailDo.setUserId(chatDetailDo.getFriendId());
        chatDetailDo.setFriendId(tmp);
        chatDetailDo.setId(null);
        chatDetailMapper.add(chatDetailDo);

        //更新这个人的未读消息内容
        FriendListDo friendListDo = userChatMapper.getFriendListByUserIdAndFriendId(chatDetailDo.getUserId(),chatDetailDo.getFriendId(),chatDetailDo.getWorkspaceId());
        friendListDo.setUnread(friendListDo.getUnread()+1);
        friendListDo.setGmtUpdate(now());
        userChatMapper.updateFriendList(friendListDo);
        log.info(friendListDo.getUserId()+"  的个人未读消息已更新");

        //要发给的人的客户端websocket通道
        Channel channel = ClientMapSingleton.getInstance().getClient(chatDetailDo.getUserId());
//        String sendMessage = JsonSerializer.serializeToJson(chatDetailDo);
        channel.writeAndFlush(new TextWebSocketFrame(JsonSerializer.serializeToJson(friendListDo)));
//        channel.writeAndFlush(new TextWebSocketFrame(sendMessage));
        log.info("已通知客户端有新消息");
    }
}

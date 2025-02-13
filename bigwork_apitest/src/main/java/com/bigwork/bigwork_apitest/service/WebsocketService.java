package com.bigwork.bigwork_apitest.service;

import io.netty.channel.Channel;

public interface WebsocketService {
    String getMessage();


    void updateChatDetail(String request);

    void setRole(String userId,Channel channel);
}

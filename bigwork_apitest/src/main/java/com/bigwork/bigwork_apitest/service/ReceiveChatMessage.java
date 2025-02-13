package com.bigwork.bigwork_apitest.service;

public interface ReceiveChatMessage {
    void receiveChatDetailDo(String message);//由channelId对应客户端发来的任务，任务类型为反序列化后的NewChatDetail
}

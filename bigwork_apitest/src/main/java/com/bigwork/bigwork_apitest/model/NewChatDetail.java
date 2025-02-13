package com.bigwork.bigwork_apitest.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("新消息内容")
public class NewChatDetail {
    private String chatId;
    private String detail;//消息内容
    private String receiveId;//接收者的id
    private String sendId;//消息发起者id


}

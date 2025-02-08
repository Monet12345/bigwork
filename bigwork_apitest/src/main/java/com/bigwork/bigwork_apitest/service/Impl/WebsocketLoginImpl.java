package com.bigwork.bigwork_apitest.service.Impl;

import cn.dev33.satoken.stp.StpUtil;

import com.bigwork.bigwork_apitest.dal.mapper.ChatDetailMapper;
import com.bigwork.bigwork_apitest.dal.mapper.UserChatMapper;
import com.bigwork.bigwork_apitest.model.ChatDetailDo;
import com.bigwork.bigwork_apitest.model.NewChatDetail;
import com.bigwork.bigwork_apitest.service.WebsocketService;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import cn.hutool.core.bean.BeanUtil;

import org.springframework.stereotype.Component;
import util.JsonSerializer;

import javax.annotation.Resource;

import static cn.hutool.core.date.DateUtil.now;

@Slf4j
@Component
public class WebsocketLoginImpl implements WebsocketService {
    @Resource
    private KafkaProducer<String, String> kafkaProducer;
    @Resource
    private ChatDetailMapper chatDetailMapper;
    @Override
    public String getMessage() {
        if(StpUtil.isLogin()){
            return "登录成功";
        }
        else {
            return "登录失败";
        }
    }

    @Override
    public void updateChatDetail(String request, Channel channel) {
        NewChatDetail newChatDetail = JsonSerializer.deserializeFromJson(request, NewChatDetail.class);
        //newChatDetail是新消息，我需要建立kafka连接，将新消息发送到kafka中等待异步消费

        //把新的内容写入数据库中
        ChatDetailDo chatDetailDo = BeanUtil.copyProperties(newChatDetail, ChatDetailDo.class);
        chatDetailDo.setGmtUpdate(now());
        chatDetailDo.setUserId(newChatDetail.getSendId());
        chatDetailDo.setFriendId(newChatDetail.getReceiveId());
        chatDetailDo.setContentUser(newChatDetail.getSendId());
        chatDetailDo.setType("TEXT");
        chatDetailDo.setContent(newChatDetail.getDetail());
        chatDetailMapper.add(chatDetailDo);

        // 使用注入的 kafkaProducer 发送消息
        ProducerRecord<String, String> record = new ProducerRecord<>("chat-list-topic", newChatDetail.getReceiveId(), JsonSerializer.serializeToJson(chatDetailDo));

        kafkaProducer.send(record);
    }
}

package com.bigwork.bigwork_meta.service.Impl;


import com.bigwork.bigwork_meta.dal.mapper.DeepSeekMapper;
import com.bigwork.bigwork_meta.dal.modle.DeepSeekDo;
import com.bigwork.bigwork_meta.model.DeepSeekMessage;

import com.bigwork.bigwork_meta.model.DeepSeekReq;
import com.bigwork.bigwork_meta.service.DeepSeekAIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import util.JsonSerializer;


import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

@Slf4j
@Component
public class DeepSeekAIServiceImpl implements DeepSeekAIService {
  @Value(value = "${deepseek-ai.first-ask}")
  private String firstAsk;
  @Resource
  private DeepSeekMapper deepSeekMapper;
  private static final String DEEPSEEK_API_URL = "https://api.deepseek.com/chat/completions";
  @Override
  public String ask(String question,String workspaceId) throws IOException, InterruptedException {

    DeepSeekDo deepSeekDo= deepSeekMapper.getMessage(workspaceId);
    if(deepSeekDo==null){
      DeepSeekDo deepSeekDo1 = new DeepSeekDo();
      deepSeekDo1.setWorkspaceId(workspaceId);
      List<DeepSeekMessage> deepSeekMessages=new ArrayList<>();
      deepSeekMessages.add(new DeepSeekMessage("user",firstAsk));
      deepSeekDo1.setDeepSeekMessages(JsonSerializer.serializeToJson(deepSeekMessages));
      deepSeekDo1.setModel("deepseek-chat");
      deepSeekDo1.setMax_tokens(50L);
      deepSeekDo1.setCreateTime(now());
      deepSeekDo1.setUpdateTime(now());
      deepSeekMapper.insert(deepSeekDo1);
      deepSeekDo= deepSeekMapper.getMessage(workspaceId);
    }
    String messages = deepSeekDo.getDeepSeekMessages();
    List<DeepSeekMessage> deepSeekMessages = JsonSerializer.deserializeFromJson(
        messages,
        new TypeReference<List<DeepSeekMessage>>() {}
    );
    //放入现在的问题
    DeepSeekMessage realMessage= new DeepSeekMessage();
    realMessage.setRole("user");
    realMessage.setContent(question);
    deepSeekMessages.add(realMessage);


   DeepSeekReq deepSeekReq = new DeepSeekReq();
    deepSeekReq.setDeepSeekMessages(deepSeekMessages);
    deepSeekReq.setModel("deepseek-chat");
    deepSeekReq.setMax_tokens(50L);
    String requisition = JsonSerializer.serializeToJson(deepSeekReq);
    JsonSerializer.deserializeFromJson(requisition, DeepSeekReq.class);


    // 创建HttpClient实例
    HttpClient client = HttpClient.newHttpClient();
    // 创建HttpRequest实例并设置请求头
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(DEEPSEEK_API_URL))
        .header("Authorization", "Bearer " + "sk-df24cb1d588148479574f3e365cd7233") // 设置Authorization头
        .header("Content-Type", "application/json") // 设置Content-Type头
        .POST( HttpRequest.BodyPublishers.ofString(requisition)) // 指定请求方法为POST
        .build();
    // 发送请求并获取响应
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());




    ObjectMapper objectMapper = new ObjectMapper();
    DeepSeekMessage message2= new DeepSeekMessage();
    try {
      // 将 JSON 字符串解析为 JsonNode
      JsonNode rootNode = objectMapper.readTree(response.body());

      // 提取 choices 数组中的第一个元素的 message
      JsonNode messageNode = rootNode
          .path("choices") // 获取 choices 数组
          .get(0)         // 获取第一个元素
          .path("message"); // 获取 message 对象

      // 将 messageNode 反序列化为 DeepSeekMessage 对象
       message2 = objectMapper.treeToValue(messageNode, DeepSeekMessage.class);

      deepSeekMessages.add(message2);

      deepSeekDo.setDeepSeekMessages(JsonSerializer.serializeToJson(deepSeekMessages));
      deepSeekMapper.update(deepSeekDo.getDeepSeekMessages(),deepSeekDo.getWorkspaceId());
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Failed to extract message from JSON.");
    }

    return message2.getContent();
  }
  @Override
  public String setFirstAskAndAsk(String firstAsk,String question,String workspaceId) throws IOException, InterruptedException {
    this.firstAsk=firstAsk;
    return this.ask(question,workspaceId);
  }
}

package com.bigwork.bigwork_apitest.common;


import com.bigwork.bigwork_apitest.service.WebsocketService;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import model.ClientMapSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.JsonSerializer;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

  @Resource
  private WebsocketService websocketService;
  // 保存所有连接的客户端 Channel的map名为ClientMapSingleton

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
    if (frame instanceof TextWebSocketFrame) {
      String request = ((TextWebSocketFrame) frame).text();

      System.out.println("Server received: " + request);
      ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器已成功接收到消息 :" + request));
      //request当中存在sendType，根据sendType进行判断，然后调用对应的方法
      ObjectMapper objectMapper = new ObjectMapper();
      String sendType=null;
      try {
        JsonNode jsonNode = objectMapper.readTree(request);
        sendType = jsonNode.get("sendType").asText();
      } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
      }
      switch (sendType) {
        case "updateChatDetail"://有用户发送了新消息，需要传入NewChatDetail类型
          websocketService.updateChatDetail(request);

        case "setRole":
          websocketService.setRole(request, ctx.channel());
          //刚连上以后发前端发个消息，只传当前连的这个人的userId，告诉后端这个客户端是谁的
          break;
      }


    } else {
      String message = "unsupported frame type: " + frame.getClass().getName();
      throw new UnsupportedOperationException(message);
    }


  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }


  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
    // 客户端连接时，保存 Channel
    String clientId = ctx.channel().id().asShortText(); // 使用 Channel ID 作为客户端标识
    System.out.println("Client connected: " + clientId);

    // 延迟一秒后发送消息
    ctx.executor().schedule(() -> {
      if (ctx.channel().isActive()) { // 确保 Channel 仍然活跃
        ctx.channel().writeAndFlush(new TextWebSocketFrame("连接成功,请在该连接发消息告诉后端这个客户端对应的userId"));
      }
    }, 5, TimeUnit.SECONDS); // 延迟 1 秒
  }




  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) {
    // 客户端断开连接时，移除 Channel
    String clientId = ctx.channel().id().asShortText();
    ctx.channel().writeAndFlush(new TextWebSocketFrame("通道关闭"));
    ClientMapSingleton.getInstance().removeClient(clientId);
    System.out.println("Client disconnected: " + clientId);
  }

  // 向指定客户端发送消息
  public static void sendToClient(String clientId, WebsocketService massageMethod) {
    Channel channel = ClientMapSingleton.getInstance().getClient(clientId);
    if (channel != null && channel.isActive()) {
      channel.writeAndFlush(new TextWebSocketFrame(massageMethod.getMessage()));
    } else {
      log.info("Client " + clientId + " is not connected.");
    }
  }
}
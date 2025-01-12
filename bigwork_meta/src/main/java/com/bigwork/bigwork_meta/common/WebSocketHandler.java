package com.bigwork.bigwork_meta.common;

import com.bigwork.bigwork_meta.service.WebsocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

  // 保存所有连接的客户端 Channel
  private static final ConcurrentHashMap<String, Channel> clients = new ConcurrentHashMap<>();
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
    if (frame instanceof TextWebSocketFrame) {
      String request = ((TextWebSocketFrame) frame).text();
      System.out.println("Server received: " + request);
      ctx.channel().writeAndFlush(new TextWebSocketFrame("Server received: " + request));
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
    clients.put(clientId, ctx.channel());
    System.out.println("Client connected: " + clientId);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) {
    // 客户端断开连接时，移除 Channel
    String clientId = ctx.channel().id().asShortText();
    clients.remove(clientId);
    System.out.println("Client disconnected: " + clientId);
  }
  // 向指定客户端发送消息
  public static void sendToClient(String clientId, WebsocketService massageMethod) {
    Channel channel = clients.get(clientId);
    if (channel != null && channel.isActive()) {
      channel.writeAndFlush(new TextWebSocketFrame(massageMethod.getMessage()));
    } else {
      log.info("Client " + clientId + " is not connected.");
    }
  }
}
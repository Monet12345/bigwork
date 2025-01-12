package com.bigwork.bigwork_apitest.web;

import com.bigwork.bigwork_apitest.model.FriendListReq;
import com.bigwork.bigwork_apitest.model.FriendVo;
import com.bigwork.bigwork_apitest.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Page;
import model.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/chat")
@Api(value = "聊天相关接口")
public class ChatController {
  @Resource private ChatService chatService;
  @PostMapping("/friendList")
  @ApiOperation(value = "好友列表")
  public Result<Page<FriendVo>> friendList(@RequestBody FriendListReq friendListReq) {

    return Result.buildSuccess(chatService.getFriendList(friendListReq));
  }

}

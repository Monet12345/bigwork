package com.bigwork.bigwork_apitest.web;

import com.bigwork.bigwork_apitest.service.WebsocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@Api(tags ="测试接口")
public class testController {
    @Resource
    private WebsocketService websocketService;
    @GetMapping("/test")
    @ApiOperation(value = "测试接口")
    public String test(){
//        websocketService.updateChatDetail("test",null);
        return "test";
    }
}

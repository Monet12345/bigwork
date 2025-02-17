package com.bigwork.bigwork_apitest.web.facade;

import api.NettyServiceFacade;
import com.bigwork.bigwork_apitest.common.NettyServer;
import io.swagger.annotations.Api;
import model.Result;
import org.springframework.web.bind.annotation.RestController;
import util.BizException;

import javax.annotation.Resource;

@RestController
@Api(tags ="Netty的内部调用facade")
public class NettyServerController implements NettyServiceFacade {
    @Resource
    private NettyServer nettyServer;

    @Override
    public Result<String> start(){
        try {
            nettyServer.start();
        }
        catch (Exception e){
            return Result.buildFailure("websocket启动失败");
        }

        return Result.buildSuccess("启动成功");
    }

    @Override
    public Result<String> stop() {
        try {
            nettyServer.stop();
        }
        catch (Exception e){
            return Result.buildFailure("websocket断开失败");
        }

        return Result.buildSuccess("断开成功");

    }
}

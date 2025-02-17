package api;

import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "nettyServiceFacade",
        url = "${bigwork.apitest.server}",
        path = "${bigwork.apitest.path}",
        primary = false)
public interface NettyServiceFacade {
    String INTERNAL_BASE="/internal";
    String BASE="/nettyServiceFacade";
    @GetMapping(INTERNAL_BASE+BASE+"/start")
    @ApiOperation(value = "启动netty服务")
    Result<String> start() throws InterruptedException;
    @GetMapping(INTERNAL_BASE+BASE+"/stop")
    @ApiOperation(value = "停止netty服务")
    Result<String> stop();

}

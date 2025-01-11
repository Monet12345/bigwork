package com.bigwork.bigwork_meta.web.facade;

import api.DeepSeekAIUtilFacade;
import com.bigwork.bigwork_meta.service.DeepSeekAIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@Api(tags = "AI问答小工具")
public class DeepSeekAIUtilController implements DeepSeekAIUtilFacade {
    @Resource
    private DeepSeekAIService deepSeekAIService;
    @Override
    public Result<String> setFirstAskAndAsk(@RequestParam String firstAsk,String question,String workspaceId) throws IOException, InterruptedException {
        return Result.buildSuccess(deepSeekAIService.setFirstAskAndAsk(firstAsk,question,workspaceId));
    }

}

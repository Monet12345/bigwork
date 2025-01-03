package com.bigwork.bigwork_meta.web.controller;

import com.bigwork.bigwork_meta.service.DeepSeekAIService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/deepseek/ai")
@Api(tags = "AI相关")
public class DeepSeekAI {
    @Resource
    private DeepSeekAIService deepSeekAIService;
    @GetMapping("/ask")
    @ApiOperation(value = "AI文字问答")
    public Result<String>  ask(@RequestParam String question,String workspaceId) throws IOException, InterruptedException {
        return Result.buildSuccess(deepSeekAIService.ask(question,workspaceId));
    }

}

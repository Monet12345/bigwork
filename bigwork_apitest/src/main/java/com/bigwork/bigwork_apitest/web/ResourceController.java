package com.bigwork.bigwork_apitest.web;

import com.bigwork.bigwork_apitest.model.*;

import com.bigwork.bigwork_apitest.service.ResourceService;
import feign.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Page;
import model.PageForm;
import model.Result;
import org.springframework.web.bind.annotation.*;
import util.BizException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/resource")
@Api(tags ="资源共享相关接口")
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    @PostMapping("/submitList")
    @ApiOperation(value = "提交表单")
    Result<String>submitList(@RequestBody ResourceReq resourceReq){
        resourceService.submitList(resourceReq);
        return Result.buildSuccess();
    }
    @GetMapping("/deleteList")
    @ApiOperation(value = "删除表接口")
    Result<String>deleteList(@RequestParam String resourceDataId,String workspaceId,String iteration){
        resourceService.deleteList(resourceDataId,workspaceId,iteration);
        return Result.buildSuccess();
    }
    @PostMapping("/getList")
    @ApiOperation(value = "分页获取表")
    Result<Page<VagueResourceVo>>getListPage(@RequestBody ListPageReq listPageReq){
        return Result.buildSuccess(resourceService.getListPage(listPageReq));
    }
    @PostMapping("/getIterationTree")
    @ApiOperation(value = "获取表的版本树")
    Result<List<List<String>>>getIterationTree(@RequestParam String resourceDataId,String workspaceId){
        return Result.buildSuccess(resourceService.getIterationTree(resourceDataId,workspaceId));
    }


    @GetMapping("/getListDetail")
    @ApiOperation(value = "根据Id和版本号获取表详情")
    Result<ResourceVo>getListDetail(@RequestParam String resourceDataId, String iteration,String workspaceId){
        return Result.buildSuccess(resourceService.getListDetail(resourceDataId,iteration,workspaceId));
    }












}

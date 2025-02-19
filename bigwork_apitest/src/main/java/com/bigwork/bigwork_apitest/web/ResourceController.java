package com.bigwork.bigwork_apitest.web;

import com.bigwork.bigwork_apitest.model.ListPageReq;
import com.bigwork.bigwork_apitest.model.ResourceReq;

import com.bigwork.bigwork_apitest.model.VagueResourceVo;
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

@RestController
@RequestMapping("/resource")
@Api(tags ="资源共享相关接口")
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    @PostMapping("/createList")
    @ApiOperation(value = "建表接口")
    Result<String>createList(@RequestBody ResourceReq resourceReq){
        resourceService.createList(resourceReq);
        return Result.buildSuccess();
    }
    @GetMapping("/deleteList")
    @ApiOperation(value = "删除表接口")
    Result<String>deleteList(@RequestParam String resourceDateId,String workspaceId){
        resourceService.deleteList(resourceDateId,workspaceId);
        return Result.buildSuccess();
    }
    @PostMapping("/getList")
    @ApiOperation(value = "分页获取表")
    Result<Page<VagueResourceVo>>getListPage(@RequestBody ListPageReq listPageReq){
        return Result.buildSuccess(resourceService.getListPage(listPageReq));
    }




}

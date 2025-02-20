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
    @GetMapping("/getListDetail")
    @ApiOperation(value = "根据Id获取表详情")
    Result<ResourceVo>getListDetail(@RequestParam String resourceDateId, String workspaceId){
        return Result.buildSuccess(resourceService.getListDetail(resourceDateId,workspaceId));
    }

    @PostMapping("/searchList")
    @ApiOperation(value = "根据表名称搜索匹配的表")
    Result<Page<VagueResourceVo>>searchList(@RequestBody SearchListReq searchListReq){

        return Result.buildSuccess(resourceService.searchList(searchListReq));
    }
    @PostMapping("/addListMessage")
    @ApiOperation(value = "往一张表中添加一条信息")
    Result<String>addListMessage(@RequestBody ResourceReq resourceReq){
        resourceService.addListMessage(resourceReq);
        return Result.buildSuccess();
    }






}

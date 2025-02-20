package com.bigwork.bigwork_apitest.service.Impl;

import api.IdManagementFacade;
import cn.hutool.core.bean.BeanUtil;
import com.bigwork.bigwork_apitest.dal.mapper.ResourceDataMapper;

import com.bigwork.bigwork_apitest.dal.mapper.ResourceIterationDataMapper;

import com.bigwork.bigwork_apitest.model.*;

import com.bigwork.bigwork_apitest.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import model.ClientMapSingleton;
import model.Page;
import model.PageForm;
import org.springframework.stereotype.Component;
import util.ArrayUtil;
import util.BizException;
import util.JsonSerializer;

import javax.annotation.Resource;

import java.util.List;

import static cn.hutool.core.date.DateUtil.now;

@Component
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    @Resource
    private ResourceDataMapper resourceDataMapper;
    @Resource
    private ResourceIterationDataMapper resourceIterationDataMapper;
    @Resource
    private IdManagementFacade idManagementFacade;
    @Override
    public void createList(ResourceReq resourceReq) {
            if(resourceReq.getName().isEmpty()){
                throw new BizException("建表时表名不能为空");
            }
            else {
                ResourceDo resourceDo= BeanUtil.copyProperties(resourceReq,ResourceDo.class);
                resourceDo.setResourceDateId(idManagementFacade.getNextId("R").getData());
                resourceDo.setGmtCreate(now());
                resourceDo.setGmtUpdate(now());
                resourceDo.setCreateUserId(resourceReq.getUpdateUserId());
                resourceDo.setUpdateUserId(resourceReq.getUpdateUserId());
                resourceDo.setExist("1");
                resourceDo.setIteration(1);
                resourceDataMapper.createList(resourceDo);
                ResourceIterationDo resourceIterationDo= new ResourceIterationDo();
                resourceIterationDo.setNowIteration(1);
                resourceIterationDo.setLastIteration(1);
                resourceIterationDo.setResourceDateId(resourceDo.getResourceDateId());
                resourceIterationDataMapper.createIteration(resourceIterationDo);
            }



    }

    @Override
    public void deleteList(String resourceDateId,String workspaceId) {
        resourceDataMapper.deleteList(resourceDateId,workspaceId);
    }

    @Override
    public Page<VagueResourceVo> getListPage(ListPageReq listPageReq) {
        List<ResourceDo> resourceDos = resourceDataMapper.getListInPage(listPageReq);
        return ArrayUtil.listToPage(resourceDos,listPageReq.getPageNo(),listPageReq.getPageSize(),resourceDataMapper.getListInPageCount(listPageReq.getWorkspaceId()),VagueResourceVo.class);
    }

    @Override
    public ResourceVo getListDetail(String resourceDateId, String workspaceId) {
        ResourceDo resourceDo = resourceDataMapper.getListDetailById(resourceDateId, workspaceId);
        return BeanUtil.copyProperties(resourceDo, ResourceVo.class);
    }

    @Override
    //根据name模糊匹配搜索表信息
    public Page<VagueResourceVo> searchList(SearchListReq searchListReq) {
        List<ResourceDo> resourceDos = resourceDataMapper.searchList(searchListReq);
        return ArrayUtil.listToPage(resourceDos,searchListReq.getPageNo(),searchListReq.getPageSize(),resourceDataMapper.searchListCount(searchListReq.getName(),searchListReq.getWorkspaceId()),VagueResourceVo.class);

    }

    @Override
    public void addListMessage(ResourceReq resourceReq) {
        synchronized (this) {
        ResourceDataDto resourceNewDataDto = JsonSerializer.deserializeFromJson(resourceReq.getData(),ResourceDataDto.class);
        ResourceDo resourceDo = resourceDataMapper.getListDetailById(resourceReq.getResourceDateId(),resourceReq.getWorkspaceId());
        ResourceDataDto resourceDataDto=JsonSerializer.deserializeFromJson(resourceDo.getData(),ResourceDataDto.class);
        resourceNewDataDto.getHead().forEach(resourceDataDto.getHead()::add);
        resourceNewDataDto.getBody().forEach(resourceDataDto.getBody()::add);
        resourceDo.setData(JsonSerializer.serializeToJson(resourceDataDto));
            ResourceIterationDo resourceIterationDo=resourceIterationDataMapper.getIterationById(resourceReq.getResourceDateId());
            ResourceIterationDo nowResourceIterationDo=resourceIterationDo;
            nowResourceIterationDo.setLastIteration(resourceIterationDo.getNowIteration()+1);
            nowResourceIterationDo.setNowIteration(resourceIterationDo.getNowIteration()+1);
            resourceIterationDataMapper.update(nowResourceIterationDo);
            resourceDo.setIteration(nowResourceIterationDo.getNowIteration());
            resourceDo.setExist("1");
            resourceDo.setGmtUpdate(now());
            resourceDataMapper.updateList(resourceDo);
        }


    }

}

package com.bigwork.bigwork_apitest.service.Impl;

import api.IdManagementFacade;
import cn.hutool.core.bean.BeanUtil;
import com.bigwork.bigwork_apitest.dal.mapper.ResourceDataMapper;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.ArrayList;
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

    //提交表单，当需要新建时，resourceDataId和iteration均传父节点的值
    @Override
    public void submitList(ResourceReq resourceReq) {

        //当表已经存在时，做覆盖，不存在时做新建
        ResourceDo resourceDo1 = resourceDataMapper.getListDetailById(resourceReq.getResourceDataId(),resourceReq.getIteration(),resourceReq.getWorkspaceId());
        if(resourceDo1!=null){
            ResourceDo resourceDo2=resourceDo1;
            resourceDo2.setName(resourceReq.getName());
            resourceDo2.setData(resourceReq.getData());
            resourceDo2.setUpdateUserId(resourceReq.getUpdateUserId());
            resourceDo2.setGmtUpdate(now());
            resourceDataMapper.updateList(resourceDo2);

            //覆盖后让数据同步到后面的节点



        }
        else{
            //新建操作
            if(resourceReq.getName().isEmpty()){
                throw new BizException("建表时表名不能为空");
            }
            else {
                //
                ResourceDo resourceDo= BeanUtil.copyProperties(resourceReq,ResourceDo.class);
                resourceDo.setResourceDataId(idManagementFacade.getNextId("Resource"+"workspaceId").getData());
                resourceDo.setGmtCreate(now());
                resourceDo.setGmtUpdate(now());
                resourceDo.setCreateUserId(resourceReq.getUpdateUserId());
                resourceDo.setUpdateUserId(resourceReq.getUpdateUserId());
                resourceDo.setExist(1);
                String iteration = idManagementFacade.getNextId("Iteration").getData();
                resourceDo.setIteration(iteration);
                resourceDataMapper.createList(resourceDo);
                ResourceIterationDo resourceIterationDo= BeanUtil.copyProperties(resourceReq,ResourceIterationDo.class);
                resourceIterationDo.setIteration(iteration);
                resourceIterationDo.setResourceDataId(resourceDo.getResourceDataId());
                resourceIterationDo.setParent(resourceReq.getIteration());
                resourceIterationDo.setGmtUpdate(now());
                resourceIterationDo.setGmtCreate(resourceDo.getGmtCreate());
                resourceIterationDataMapper.add(resourceIterationDo);
                ResourcePathMap.getInstance().addPoint(resourceIterationDo.getIteration(),resourceIterationDo.getResourceDataId(),resourceIterationDo.getWorkspaceId());
            }
        }
    }

    @Override
    public void deleteList(String resourceDataId,String workspaceId,String iteration) {
                ResourceDo resourceDo = resourceDataMapper.getListDetailById(resourceDataId,iteration, workspaceId);
                if(resourceDo==null){
                    throw new BizException("表不存在");
                }
        resourceDataMapper.deleteList(resourceDataId,iteration,workspaceId);
        ResourcePathMap.getInstance().deletePoint(resourceDo.getIteration(),resourceDo.getResourceDataId(),resourceDo.getWorkspaceId());

    }

    @Override
    public Page<VagueResourceVo> getListPage(ListPageReq listPageReq) {
        List<VagueResourceVo> resourceDos = resourceDataMapper.getListInPage(listPageReq);
        return ArrayUtil.listToPage(resourceDos,listPageReq.getPageNo(),listPageReq.getPageSize(),resourceDataMapper.getListInPageCount(listPageReq.getWorkspaceId()),VagueResourceVo.class);
    }



    @Override
    public ResourceVo getListDetail(String resourceDataId,String iteration, String workspaceId) {
        ResourceDo resourceDo = resourceDataMapper.getListDetailById(resourceDataId, iteration,workspaceId);
        return BeanUtil.copyProperties(resourceDo, ResourceVo.class);
    }


    @Override
    public List<List<String>> getIterationTree(String resourceDataId, String workspaceId) {
        return ResourcePathMap.getInstance().getIterationTree(resourceDataId,workspaceId);
    }





}

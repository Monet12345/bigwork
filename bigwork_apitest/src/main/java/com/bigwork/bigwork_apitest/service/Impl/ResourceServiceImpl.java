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
import util.BizException;

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
                resourceDo.setIteration("1");
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
        List<VagueResourceVo> vagueResourceVos = resourceDos.stream().map(resourceDo -> {
            return BeanUtil.copyProperties(resourceDo, VagueResourceVo.class);
        }).toList();
        Page<VagueResourceVo> VagueResourceVoPage = new Page<>();
        VagueResourceVoPage.setList(vagueResourceVos);
        VagueResourceVoPage.setPageNo(listPageReq.getPageNo());
        VagueResourceVoPage.setPageSize(listPageReq.getPageSize());
        VagueResourceVoPage.setTotal(resourceDataMapper.getListInPageCount(listPageReq.getWorkspaceId()));
        return VagueResourceVoPage;
    }

}

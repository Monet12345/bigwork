package com.bigwork.bigwork_meta.web.controller;


import api.IdManagementFacade;
import com.bigwork.bigwork_meta.service.IdManagementService;
import io.swagger.annotations.Api;
import model.Result;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "id管理服务")
public class IdManagementController implements IdManagementFacade {
  @Resource
  private IdManagementService idManagementService;
  @Override
  public Result<String> getNextId(String head){
    return Result.buildSuccess(idManagementService.getNextId(head));
  }
}

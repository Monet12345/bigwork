package com.bigwork.bigwork_meta.service.Impl;

import com.bigwork.bigwork_meta.dal.mapper.IdManagementMapper;
import com.bigwork.bigwork_meta.service.IdManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import util.BizException;

import javax.annotation.Resource;

import java.util.Date;

import static cn.hutool.core.date.DateTime.now;

@Slf4j
@Component
public class IdManagementServiceImpl implements IdManagementService {
  private int maxRetryTimes = 100;
  @Resource private IdManagementMapper idManagementMapper;
  @Override
  public String getNextId(String head) {
    int retryTimes = 0;
    Long value = idManagementMapper.getByKey(head);
    if(value==null){
      try{
        idManagementMapper.insert(head,1L,now(),now());
        return head+"1";
      }
      catch (DuplicateKeyException e){
        log.warn("插入id失败，来自IdManagementServiceImpl");
        value = idManagementMapper.getByKey(head);
        if (value == null) {
          // 如果又查不到了，属于非预期情况，直接抛异常
          throw new RuntimeException("来自IdManagementServiceImpl，Cannot find sequence: " + value);
        }
      }
    }
    while (true){
      retryTimes++;
      if(retryTimes>=maxRetryTimes){
          throw new BizException("来自IdManagementServiceImpl，重试次数超过100，更新，获取下一个id失败: " + value);
      }
      Date updateTime = new Date();
      value= idManagementMapper.getByKey(head);
      int row=idManagementMapper.update(head,value+1,updateTime);//判断是否更新成功
      if(row==1){
        return head+(value+1);
      }
      value= idManagementMapper.getByKey(head);
      if(value==null){
        throw new BizException("来自IdManagementServiceImpl，更新操作未命中: " + value);

      }

    }



  }
}

package com.bigwork.bigwork_meta.dal.mapper;

import org.mapstruct.Mapper;

import java.util.Date;

@Mapper
public interface IdManagementMapper {
  public Long getByKey(String head);
  public int update(String head,Long value,Date updateTime);
  public void insert(String head, Long value, Date createTime, Date updateTime);
}

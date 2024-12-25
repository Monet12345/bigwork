package com.bigwork.bigwork_meta.dal.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface IdManagementMapper {
   Long getByKey(String head);
   int update(String head,Long value,Date updateTime);
   void insert(String head, Long value, Date createTime, Date updateTime);
}

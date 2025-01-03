package com.bigwork.bigwork_meta.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface IdManagementMapper {
   Long getByKey(@Param("head")String head);
   int update(@Param("head")String head,@Param("value")Long value,@Param("updateTime")Date updateTime);
   void insert(@Param("head") String head, @Param("value") Long value, @Param("createTime") Date createdAt, @Param("updateTime") Date updatedAt);
}

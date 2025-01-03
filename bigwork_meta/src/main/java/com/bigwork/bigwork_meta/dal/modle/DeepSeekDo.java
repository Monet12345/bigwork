package com.bigwork.bigwork_meta.dal.modle;

import lombok.Data;

import java.util.Date;


@Data
public class DeepSeekDo {
  private String model;
  private String deepSeekMessages;
  private Long max_tokens;
  private Date createTime;
  private Date updateTime;
  private String workspaceId;
}

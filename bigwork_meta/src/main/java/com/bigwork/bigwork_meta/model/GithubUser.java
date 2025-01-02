package com.bigwork.bigwork_meta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GithubUser {
  @JsonProperty("login")
  private String nickName;

  @JsonProperty("id")
  private Long userName; // 修改为 Long 类型

  @JsonProperty("email")
  private String email;

  @JsonProperty("created_at") // 修正拼写错误
  private String createTime;

  @JsonProperty("updated_at") // 修正拼写错误
  private String updateTime;

  private String workspaceId; // 如果需要映射，确保 JSON 中有对应字段
}

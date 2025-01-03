package com.bigwork.bigwork_meta.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DeepSeekReq {
  private String model;
  @JsonProperty("messages")
  private List<DeepSeekMessage> deepSeekMessages;
  private Long max_tokens;
}

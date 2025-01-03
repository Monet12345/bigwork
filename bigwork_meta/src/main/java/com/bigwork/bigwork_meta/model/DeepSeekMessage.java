package com.bigwork.bigwork_meta.model;

import lombok.Data;

@Data
public class DeepSeekMessage {
    // 无参构造函数
    public DeepSeekMessage() {}
    private String role;
    private String content;
    public DeepSeekMessage(String role, String content){
        this.role = role;
        this.content = content;
    }
}

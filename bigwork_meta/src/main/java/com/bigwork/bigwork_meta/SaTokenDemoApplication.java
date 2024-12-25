package com.bigwork.bigwork_meta;

import cn.dev33.satoken.SaManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bigwork.bigwork_meta.dal")
public class SaTokenDemoApplication {
  public static void main(String[] args) throws JsonProcessingException {
    SpringApplication.run(SaTokenDemoApplication.class, args);
    System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
  }
}
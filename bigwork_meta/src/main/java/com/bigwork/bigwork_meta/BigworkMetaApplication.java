package com.bigwork.bigwork_meta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.bigwork.bigwork_meta.dal.mapper")
@EnableFeignClients(basePackages = "api")
public class BigworkMetaApplication {


    public static void main(String[] args) {
        SpringApplication.run(BigworkMetaApplication.class, args);
    }
}

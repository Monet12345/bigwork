package com.bigwork.bigwork_apitest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.bigwork.bigwork_apitest.dal.mapper")
@EnableFeignClients(basePackages = "api")
public class BigworkApitestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigworkApitestApplication.class, args);
    }
}

package com.bigwork.bigwork_apitest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bigwork.bigwork_apitest.dal.mapper")
public class BigworkApitestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigworkApitestApplication.class, args);
    }
}

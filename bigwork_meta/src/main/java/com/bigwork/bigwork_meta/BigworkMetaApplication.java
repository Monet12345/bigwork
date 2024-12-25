package com.bigwork.bigwork_meta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bigwork.bigwork_meta.dal.mapper")
public class BigworkMetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigworkMetaApplication.class, args);
    }
}

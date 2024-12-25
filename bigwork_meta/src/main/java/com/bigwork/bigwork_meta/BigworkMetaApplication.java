package com.bigwork.bigwork_meta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.bigwork.bigwork_meta.dal")
public class BigworkMetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigworkMetaApplication.class, args);
    }

}

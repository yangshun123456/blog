package com.ysmork.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.ysmork.blog.dao")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run (BlogApplication.class, args);
    }

}

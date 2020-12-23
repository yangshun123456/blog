package com.ysmork.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yangshun
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@MapperScan("com.ysmork.blog.dao")
@EnableAsync
@EnableScheduling
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run (BlogApplication.class, args);
    }

}

package com.hsms.publisher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 发布者信息启动类
 *
 * @author haotchen
 * @time 2022/11/10-17:52
 */

@SpringBootApplication
@MapperScan("com.hsms.core.mapper")
@ComponentScan("com.hsms")
@EnableTransactionManagement
public class PublisherApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class,args);
    }
}

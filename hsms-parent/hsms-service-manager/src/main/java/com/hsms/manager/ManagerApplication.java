package com.hsms.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Manager启动类
 *
 * @author haotchen
 * @time 2022/11/8-17:03
 */
@SpringBootApplication
@MapperScan("com.hsms.core.mapper")
@ComponentScan("com.hsms")
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}

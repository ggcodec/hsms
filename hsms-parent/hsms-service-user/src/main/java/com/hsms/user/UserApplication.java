package com.hsms.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户信息模块启动类
 *
 * @author haotchen
 * @time 2022/11/7-17:18
 */
@SpringBootApplication
@MapperScan("com.hsms.core.mapper")
@ComponentScan("com.hsms")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class,args);
    }
}

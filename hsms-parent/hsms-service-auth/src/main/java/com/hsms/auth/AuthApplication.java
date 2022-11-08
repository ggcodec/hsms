package com.hsms.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证模块启动类
 *
 * @author haotchen
 * @time 2022/11/8-12:54
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.hsms.*.api")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class,args);
    }
}

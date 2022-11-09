package com.hsms.staff;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Staff模块启动类
 *
 * @author haotchen
 * @time 2022/11/9-13:27
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.hsms.core.mapper")
@ComponentScan("com.hsms")
public class StaffApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaffApplication.class,args);
    }
}

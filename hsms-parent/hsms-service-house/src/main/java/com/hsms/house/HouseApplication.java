package com.hsms.house;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 房源信息模块启动类
 *
 * @author haotchen
 * @time 2022/11/13-17:54
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.hsms.core.mapper")
@ComponentScan("com.hsms")
public class HouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(HouseApplication.class,args);
    }
}

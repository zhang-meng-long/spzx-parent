package com.zq.service.car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author 张乔
 * @Date 2023/11/18 15:09
 */
//取消数据库的自动配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = "com.zq.client")
public class CarApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarApplication.class,args);
    }
}

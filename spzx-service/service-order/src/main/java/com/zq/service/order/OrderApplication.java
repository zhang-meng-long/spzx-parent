package com.zq.service.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author 张乔
 * @Date 2023/11/18 15:03
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.zq.client")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

}

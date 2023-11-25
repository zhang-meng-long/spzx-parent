package com.zq.service.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author 张乔
 * @Date 2023/11/20 14:22
 */

@SpringBootApplication

//远程调用接口
@EnableFeignClients("com.zq.client")
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class , args) ;
    }


}

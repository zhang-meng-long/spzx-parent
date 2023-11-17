package com.zq.service.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:31
 */
@SpringBootApplication
@EnableCaching
public class ProductApplication {
    public static void main(String[] args) {

        SpringApplication.run(ProductApplication.class, args);

    }
}

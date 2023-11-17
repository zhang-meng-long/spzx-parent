package com.example.manager.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 张乔
 * @Date 2023/11/2 12:57
 */

@Configuration
public class MybatisPlusInterceptorConfig {

//    mybatisPlus分页拦截器
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
       MybatisPlusInterceptor mybatisPlusInterceptor=new MybatisPlusInterceptor();
       mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }









}

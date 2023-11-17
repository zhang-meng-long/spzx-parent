package com.example.manager;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author 张乔
 * @Date 2023/11/3 15:19
 */
@SpringBootTest
public class test01 {
    @Test
    void send(){
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);


        System.out.println(StrUtil.isEmpty(""));
    }


}

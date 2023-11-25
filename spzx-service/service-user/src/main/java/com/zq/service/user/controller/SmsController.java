package com.zq.service.user.controller;

import com.example.emailspringbootstarter.service.SendEmail;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author 张乔
 * @Date 2023/11/17 20:55
 */
@RestController
@RequestMapping("api/user/sms")
public class SmsController {
    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping(value = "/sendCode/{phone}")
    public Result sendValidateCode(@PathVariable String phone) {
//发送验证码
        // 创建一个 Random 对象
        Random random = new Random();
        // 生成随机的四位数验证码
        int code = 1000 + random.nextInt(9000);
        sendEmail.sendEmail(phone,"验证码", String.valueOf(code));
//        将验证码存入redis中，设置过期时间为5分钟
        redisTemplate.opsForValue().set(phone,String.valueOf(code),
                5, TimeUnit.MINUTES);
        System.err.println(code);
        return Result.success();
    }


}

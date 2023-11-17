package com.example.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.example.manager.config.Constant;
import com.example.manager.service.validateCodeService;
import com.example.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author 张乔
 * @Date 2023/11/1 11:04
 */
@Service
public class validateCodeServiceImpl implements validateCodeService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


//    生成了验证码，还没有校验
    @Override
    public ValidateCodeVo createvalidateCode() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 3);
        String code = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();
        System.out.println(imageBase64);
String token= UUID.randomUUID().toString();

        redisTemplate.opsForValue().set(Constant.CODE +token,code,5, TimeUnit.MINUTES);


        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(token);
        validateCodeVo.setCodeValue("data:image/png;base64,"+imageBase64);

        return validateCodeVo;
    }



}

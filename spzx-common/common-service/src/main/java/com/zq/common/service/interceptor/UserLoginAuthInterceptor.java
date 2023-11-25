package com.zq.common.service.interceptor;

import com.alibaba.fastjson2.JSON;
import com.example.common.util.AutoThreadLocal;
import com.example.spzx.model.entity.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author 张乔
 * @Date 2023/11/18 13:52
 */


@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

@Autowired
private RedisTemplate<String,String> redisTemplate;

    @Override
  public   boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = redisTemplate.opsForValue().get(request.getHeader("token"));
        if (StringUtils.hasText(token)){
            AutoThreadLocal.setUserInfo(JSON.parseObject(token, UserInfo.class));
        }

        return true;
    }




}

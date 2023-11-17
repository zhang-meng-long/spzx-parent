package com.example.manager.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.example.common.util.AutoThreadLocal;
import com.example.spzx.model.entity.system.SysUser;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Author 张乔
 * @Date 2023/11/1 19:55
 */
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
@Autowired
private RedisTemplate<String,String> redisTemplate;

/*
* 在一次请求之前，得到用户携带的token，判断token是否有效，如果有效，则放行，
* 并将查到的用户数据存入到本地线程ThreadLocal中
* ，如果无效，则返回未登录信息
*
* */

//    在方法执行之前
    @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//得到请求的类型，如果为options，则为预检请求，需要放行

        String method = request.getMethod();
        if ("OPTIONS".equals(method)){
            return true;
        }

        String token = request.getHeader("token");


        if (StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }
        String redisSysUser = redisTemplate.opsForValue().get(Constant.LOGIN + token);
        if (StrUtil.isEmpty(redisSysUser)){
            responseNoLoginInfo(response);
            return false;
        }
        redisTemplate.expire(Constant.LOGIN + token, 30, TimeUnit.MINUTES);
        AutoThreadLocal.setUser(JSON.parseObject(redisSysUser, SysUser.class));

        return true;
    }

//在方法执行之后
@Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

//  在一个请求结束之后，删除ThreadLocal中的值，释放内存

AutoThreadLocal.removeUser();



    }












}

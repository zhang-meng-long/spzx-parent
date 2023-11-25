package com.spzx.gateway.filter;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.spzx.model.entity.user.UserInfo;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/18 11:25
 */
//@Order(1)
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {


    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RedisTemplate<String , String> redisTemplate;

//拦截器的逻辑
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        获取请求的路径
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        log.info("uri: "+uri);
        String path = uri.getPath();
        log.info("path: "+path);
//        得到用户信息
        UserInfo userInfo = getUserInfo(request);
        System.out.println(userInfo);
        if(antPathMatcher.match("/api/**/auth/**", path)) {
            if(userInfo==null) {
                ServerHttpResponse response = exchange.getResponse();
                return out(response, ResultCodeEnum.LOGIN_AUTH);
            }
        }
        return chain.filter(exchange);
    }


    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {

        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }







    private UserInfo getUserInfo(ServerHttpRequest request) {
        String token = "";
        List<String> tokenList = request.getHeaders().get("token");
        if(null != tokenList) {
            token = tokenList.get(0);
        }

        if(!token.isEmpty()) {
            String userInfoJSON = redisTemplate.opsForValue().get(token);
            if(!StringUtils.hasText(userInfoJSON)) {
                return null ;
            }else {
                return JSON.parseObject(userInfoJSON , UserInfo.class) ;
            }
        }
        return null;
    }









    @Override
    public int getOrder() {
        return 0;
    }
}

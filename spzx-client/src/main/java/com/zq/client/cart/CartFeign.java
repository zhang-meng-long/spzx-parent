package com.zq.client.cart;

import com.example.spzx.model.entity.h5.CartInfo;
import com.example.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/19 11:13
 * 使用OpenFeign进行远程调用，与nacos,eureka进行通信
 */
@FeignClient(value = "service-cart")
public interface CartFeign {

    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    Result<List<CartInfo>> getAllCkecked(@RequestHeader("token") String token);

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    Result deleteChecked(@RequestHeader("token") String token);


}

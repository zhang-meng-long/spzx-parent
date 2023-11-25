package com.zq.client.order;

import com.example.spzx.model.entity.order.OrderInfo;
import com.example.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 张乔
 * @Date 2023/11/20 14:29
 */

@FeignClient(value = "service-order")
public interface OrderFeignClient {

//    专门用于远程调用的
    @GetMapping("/api/order/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    Result<OrderInfo> getOrderInfoByOrderNo(@PathVariable String orderNo) ;

    @GetMapping("/api/order/orderInfo/auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
 Result updateOrderStatus(@PathVariable("orderNo") String orderNo , @PathVariable(value = "orderStatus") Integer orderStatus) ;





}
package com.zq.service.order.service;

import com.example.spzx.model.dto.h5.OrderInfoDto;
import com.example.spzx.model.entity.order.OrderInfo;
import com.example.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

/**
 * @Author 张乔
 * @Date 2023/11/19 12:22
 */

public interface OrderInfoService {
    TradeVo getTrade(String token);

    Long submitOrder(OrderInfoDto orderInfoDto,String token);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus, String token);

    OrderInfo getByOrderNo(String orderNo);

    void updateOrderStatus(String orderNo, Integer orderStatus);
}

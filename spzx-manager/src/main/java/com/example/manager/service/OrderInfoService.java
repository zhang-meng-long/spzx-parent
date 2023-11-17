package com.example.manager.service;

import com.example.spzx.model.dto.order.OrderStatisticsDto;
import com.example.spzx.model.vo.order.OrderStatisticsVo;

/**
 * @Author 张乔
 * @Date 2023/11/13 20:52
 */

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}

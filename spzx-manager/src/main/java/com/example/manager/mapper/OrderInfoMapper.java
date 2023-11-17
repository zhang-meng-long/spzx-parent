package com.example.manager.mapper;

import com.example.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 张乔
 * @Date 2023/11/13 20:35
 */

@Mapper
public interface OrderInfoMapper {
    // 查询指定日期产生的订单数据
    OrderStatistics selectOrderStatistics(String creatTime);

}

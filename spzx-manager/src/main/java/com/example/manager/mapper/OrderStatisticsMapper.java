package com.example.manager.mapper;

import com.example.spzx.model.dto.order.OrderStatisticsDto;
import com.example.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/13 20:37
 */
@Mapper
public interface OrderStatisticsMapper {


 void insert(OrderStatistics orderStatistics);


 List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);


}

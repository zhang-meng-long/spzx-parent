package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 张乔
 * @Date 2023/11/12 14:24
 */
@Mapper
public interface ProductSpecMapper extends BaseMapper<ProductSpec> {
}

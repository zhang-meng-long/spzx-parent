package com.zq.service.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.dto.h5.ProductSkuDto;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:51
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    List<ProductSku> selectTen();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);
}

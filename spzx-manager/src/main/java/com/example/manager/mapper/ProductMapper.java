package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.spzx.model.dto.product.ProductDto;
import com.example.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/12 15:18
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {


    List<Product> selectLists(ProductDto productDto);

    Product selectByIds(Long id);
}

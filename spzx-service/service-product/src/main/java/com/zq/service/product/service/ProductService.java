package com.zq.service.product.service;

import com.example.spzx.model.dto.h5.ProductSkuDto;
import com.example.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:49
 */

public interface ProductService {
    List<ProductSku> selectTen();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);
}

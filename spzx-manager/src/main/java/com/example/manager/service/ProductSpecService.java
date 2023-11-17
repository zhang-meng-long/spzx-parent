package com.example.manager.service;

import com.example.manager.config.PageInfoTest;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductSpec;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/12 14:23
 */

public interface ProductSpecService {
    PageInfoTest<ProductSpec> getList(Integer page, Integer limit);

    void save(ProductSpec product);

    void updateById(ProductSpec product);

    void deleteById(Integer id);

    List<ProductSpec> findAll();
}

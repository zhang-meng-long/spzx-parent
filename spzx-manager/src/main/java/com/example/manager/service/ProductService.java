package com.example.manager.service;

import com.example.manager.config.PageInfoTest;
import com.example.spzx.model.dto.product.ProductDto;
import com.example.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

/**
 * @Author 张乔
 * @Date 2023/11/12 15:17
 */

public interface ProductService {
    PageInfo<Product> getProductList(Integer page, Integer limit, ProductDto productDto);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}

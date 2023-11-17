package com.zq.service.product.service.impl;

import com.example.spzx.model.dto.h5.ProductSkuDto;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zq.service.product.config.Custom;
import com.zq.service.product.mapper.ProductMapper;
import com.zq.service.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:50
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;



    @Cacheable(value ="category", key = "'sku'")
    @Override
    public List<ProductSku> selectTen() {
return productMapper.selectTen();
    }

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
 List<ProductSku> productList= productMapper.findByPage(productSkuDto);
        return new PageInfo<>(productList);
    }
}

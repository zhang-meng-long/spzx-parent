package com.example.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.manager.mapper.ProductUnitMapper;
import com.example.manager.service.ProductUnitService;
import com.example.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/12 17:00
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper;



    @Override
    public List<ProductUnit> findAll() {

        List<ProductUnit> productUnits = productUnitMapper.selectList(new LambdaQueryWrapper<ProductUnit>()
                .orderByDesc(ProductUnit::getId));
        return  productUnits;
    }
}

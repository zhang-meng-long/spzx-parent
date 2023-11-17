package com.zq.service.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spzx.model.entity.product.Brand;
import com.zq.service.product.mapper.BrandMapper;
import com.zq.service.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/16 21:15
 */
@Service
public class BrandServiceImpl implements BrandService {


    @Autowired
    private BrandMapper brandMapper;

    @Cacheable(value = "brandAll")
    @Override
    public List<Brand> findAll() {

        List<Brand> list = brandMapper.selectList(new LambdaQueryWrapper<Brand>().
                orderByDesc(Brand::getId));

        return list;
    }


}

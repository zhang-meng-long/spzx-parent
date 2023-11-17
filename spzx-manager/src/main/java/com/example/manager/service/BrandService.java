package com.example.manager.service;

import com.example.manager.config.PageInfoTest;
import com.example.spzx.model.entity.product.Brand;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/7 15:37
 */

public interface BrandService {

    PageInfoTest<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void deletedById(Long id);

    void update(Brand brand);

    List<Brand> findAll();
}

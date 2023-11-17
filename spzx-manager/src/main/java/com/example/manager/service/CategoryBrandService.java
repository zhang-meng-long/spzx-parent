package com.example.manager.service;

import com.example.manager.config.PageInfoTest;
import com.example.spzx.model.dto.product.CategoryBrandDto;
import com.example.spzx.model.entity.product.Brand;
import com.example.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/7 17:28
 */

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    void updateById(CategoryBrand categoryBrand);

    void deleteById(Integer id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}

package com.example.manager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.spzx.model.dto.product.CategoryBrandDto;
import com.example.spzx.model.entity.product.Brand;
import com.example.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/7 17:29
 */
@Mapper
public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {

    List<CategoryBrand> findByPage(@Param("categoryBrandDto") CategoryBrandDto categoryBrandDto);

    List<Brand> findBrandByCategoryId(Long categoryId);
}

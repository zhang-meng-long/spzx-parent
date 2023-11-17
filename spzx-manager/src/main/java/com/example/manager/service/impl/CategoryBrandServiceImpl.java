package com.example.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manager.config.PageInfoTest;
import com.example.manager.mapper.CategoryBrandMapper;
import com.example.manager.service.CategoryBrandService;
import com.example.spzx.model.dto.product.CategoryBrandDto;
import com.example.spzx.model.entity.product.Brand;
import com.example.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/7 17:29
 */
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

@Autowired
private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page,limit);
  List<CategoryBrand> categoryBrandIPage = categoryBrandMapper.findByPage(categoryBrandDto);


        PageInfo<CategoryBrand> categoryBrandIPageInfo = new PageInfo<>(categoryBrandIPage);

        List<CategoryBrand> list = categoryBrandIPageInfo.getList();
        list.forEach(System.err::println);
        return categoryBrandIPageInfo;




    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.insert(categoryBrand);
    }

    @Override
    public void updateById(CategoryBrand categoryBrand) {
categoryBrand.setUpdateTime(new Date());
        categoryBrandMapper.updateById(categoryBrand);


    }

    @Override
    public void deleteById(Integer id) {

        CategoryBrand categoryBrand = categoryBrandMapper.selectById(id);
        categoryBrand.setUpdateTime(new Date());
categoryBrandMapper.updateById(categoryBrand);
        categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {



        return  categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}

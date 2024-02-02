package com.zq.service.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spzx.model.entity.product.Category;
import com.zq.service.product.mapper.CategoryMapper;
import com.zq.service.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:50
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


//    获取所有的一级分类

    @Cacheable(value ="category", key = "'one'")
    @Override
    public List<Category> selectOne() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId,0)
                .eq(Category::getStatus,1)
                        .orderByAsc(Category::getOrderNum);

        List<Category> categoryList = categoryMapper.selectList(wrapper);

        return categoryList;
    }


    @Cacheable(value ="category", key = "'all'")
    @Override
    public List<Category> findCategoryTree() {
//            查询全部数据
        List<Category> categoryList = categoryMapper.selectList(null);
//        挑出一级分类，ParentId=0；
        List<Category> Onecollect = categoryList.stream().
                filter(it -> it.getParentId() == 0).toList();

        Onecollect.forEach( one->{
//        挑出二级分类，并将二级分类添加到一级分类中
                  List<Category> twocategoryList = categoryList.stream().
                          filter(it -> it.getParentId() == one.getId()).toList();

          one.setChildren(twocategoryList);
//              跳出三级分类，并将三级分类添加到二级分类中
            twocategoryList.forEach(two ->{
                List<Category> categoryList3 = categoryList.stream().
                        filter(it -> it.getParentId() == two.getId()).toList();

            two.setChildren(categoryList3);
            });
              });

//将一级分类返回
        return Onecollect;
    }
}

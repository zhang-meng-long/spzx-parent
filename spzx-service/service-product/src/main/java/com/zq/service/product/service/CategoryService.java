package com.zq.service.product.service;

import com.example.spzx.model.entity.product.Category;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:49
 */

public interface CategoryService {

    List<Category> selectOne();

    List<Category> findCategoryTree();
}

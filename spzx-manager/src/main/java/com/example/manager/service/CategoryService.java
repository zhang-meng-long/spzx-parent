package com.example.manager.service;

import com.example.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/6 15:32
 */

public interface CategoryService {
    List<Category> findByParentId(Long parentId);

    void exportData(HttpServletResponse response);
}

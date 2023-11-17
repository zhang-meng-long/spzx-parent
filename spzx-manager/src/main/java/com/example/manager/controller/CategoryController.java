package com.example.manager.controller;

import com.example.manager.service.CategoryService;
import com.example.spzx.model.entity.product.Category;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/6 15:31
 */
@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {

@Autowired
    private CategoryService categoryService;


    @Operation(summary = "根据parentId获取下级节点")
    @GetMapping(value = "/findByParentId/{parentId}")
public Result findByParentId(@PathVariable("parentId") Long parentId){

      List<Category> categoryList= categoryService.findByParentId(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }


    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }








}

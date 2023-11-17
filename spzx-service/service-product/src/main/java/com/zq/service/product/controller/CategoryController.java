package com.zq.service.product.controller;

import com.alibaba.fastjson.JSON;
import com.example.spzx.model.entity.product.Category;
import com.example.spzx.model.vo.common.Result;
import com.zq.service.product.config.Custom;
import com.zq.service.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author 张乔
 * @Date 2023/11/14 16:26
 */
@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value="/api/product/category")
//@CrossOrigin  //临时解决跨域
public class CategoryController {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "获取分类树形数据")
    @GetMapping("findCategoryTree")
    public Result findCategoryTree(){
//        先从redis中查数据。查不到再从数据库中查，并将数据库中的数据存入到redis中
//        String redisData = redisTemplate.opsForValue().get(Custom.CATEGORY);
//        if (StringUtils.hasText(redisData)){
//            List<Category> categoryList = JSON.parseArray(redisData, Category.class);
//            return Result.SuccessData(categoryList);
//        }

        List<Category> list= categoryService.findCategoryTree();
//    redisTemplate.opsForValue().set(Custom.CATEGORY,JSON.toJSONString(list),
//            5, TimeUnit.DAYS);
        return Result.SuccessData(list);
    }





}

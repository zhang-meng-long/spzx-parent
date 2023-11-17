package com.zq.service.product.controller;

import com.example.spzx.model.entity.product.Category;
import com.example.spzx.model.entity.product.ProductSku;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.h5.IndexVo;
import com.zq.service.product.service.CategoryService;
import com.zq.service.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:47
 */
@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value="/api/product/index")
//@CrossOrigin  //临时解决跨域问题

public class IndexController {

@Autowired
private CategoryService categoryService;

@Autowired
private ProductService productService;



    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result  findData(){
//        获取一级分类
   List<Category> categoryList= categoryService.selectOne();
//    获取前十条畅销商品
     List<ProductSku> skuList=   productService.selectTen();
        IndexVo indexVo=new IndexVo();
        indexVo.setCategoryList(categoryList);
     indexVo.setProductSkuList(skuList);
     return Result.SuccessData(indexVo);
    }





}

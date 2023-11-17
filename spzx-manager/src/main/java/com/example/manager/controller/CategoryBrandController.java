package com.example.manager.controller;

import com.example.manager.config.PageInfoTest;
import com.example.manager.service.CategoryBrandService;
import com.example.spzx.model.dto.product.CategoryBrandDto;
import com.example.spzx.model.entity.product.Brand;
import com.example.spzx.model.entity.product.CategoryBrand;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ResultProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/7 17:28
 */
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {

    @Autowired
    public CategoryBrandService categoryBrandService;

//    分页查询所有数据
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             CategoryBrandDto CategoryBrandDto){

PageInfo<CategoryBrand> pageInfoTest= categoryBrandService.findByPage(page,limit,CategoryBrandDto);
        return Result.build(pageInfoTest, ResultCodeEnum.SUCCESS);

    }
//新增
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
//修改
    @PutMapping("updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand) {

        categoryBrandService.updateById(categoryBrand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;

    }


//    删除
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {

        categoryBrandService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId) {
        List<Brand> brandList =   categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brandList , ResultCodeEnum.SUCCESS) ;
    }



}

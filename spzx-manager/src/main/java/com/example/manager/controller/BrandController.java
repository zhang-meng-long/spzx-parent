package com.example.manager.controller;

import com.example.manager.config.PageInfoTest;
import com.example.manager.service.BrandService;
import com.example.spzx.model.entity.product.Brand;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ResultProcessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/7 15:36
 */
@RestController
@RequestMapping(value="/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer limit) {

    PageInfoTest<Brand> pageInfoTest= brandService.findByPage(page, limit);
        return Result.build(pageInfoTest, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Brand brand) {
        brandService.save(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
brandService.deletedById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PutMapping("/Update")
    public Result update(@RequestBody Brand brand){
        brandService.update(brand);
        return Result.success();
    }

    @GetMapping("/findAll")
    public Result findAll(){
   List<Brand> list= brandService.findAll();
        return Result.build(list,ResultCodeEnum.SUCCESS);

    }







}

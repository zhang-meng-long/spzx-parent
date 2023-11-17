package com.example.manager.controller;

import com.example.manager.config.PageInfoTest;
import com.example.manager.service.ProductSpecService;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductSpec;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/12 14:22
 */
@RestController
@RequestMapping(value="/admin/product/productSpec")
public class ProductSpecController {

    @Autowired
    private ProductSpecService productSpecService ;


    @GetMapping("/{page}/{limit}")
    public Result  getProductList(@PathVariable("page") Integer page, @PathVariable("limit") Integer limit){
    PageInfoTest<ProductSpec> productList= productSpecService.getList(page,limit);

        return Result.build(productList, ResultCodeEnum.SUCCESS);

    }

    @PostMapping("save")
    public Result save(@RequestBody ProductSpec product){
        productSpecService.save(product);
        return Result.success();
    }

    @PutMapping("updateById")
    public Result updateById(@RequestBody ProductSpec product){
        productSpecService.updateById(product);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public  Result deleteById(@PathVariable("id") Integer id){
        productSpecService.deleteById(id);
        return Result.success();
    }

    @GetMapping("findAll")
    public Result findAll() {
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }






}

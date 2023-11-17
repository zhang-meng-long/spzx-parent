package com.zq.service.product.controller;

import com.example.spzx.model.entity.product.Brand;
import com.example.spzx.model.vo.common.Result;
import com.zq.service.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/15 20:51
 */
@Tag(name = "品牌管理")
@RestController
@RequestMapping(value="/api/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Operation(summary = "获取全部品牌")
    @GetMapping("findAll")
    public Result findAll(){

    List<Brand> list= brandService.findAll();
        return Result.SuccessData(list);
    }



}

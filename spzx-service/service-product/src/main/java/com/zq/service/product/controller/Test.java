package com.zq.service.product.controller;

import com.example.spzx.model.entity.product.Product;
import com.zq.service.product.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/28 17:37
 */
@RestController
@RequestMapping("/user/product")
public class Test {


    @Autowired
    private ProductMapper productMapper;

    //返回一些写死的商品数据，用来暂时展示浏览记录

    @GetMapping
    public List<Product> get(){
       List<Integer> ids=new ArrayList<>();
       ids.add(1);
       ids.add(2);
       ids.add(3);
       ids.add(7);
       ids.add(8);
        List<Product> productList = productMapper.selectBatchIds(ids);
return productList;
    }



}

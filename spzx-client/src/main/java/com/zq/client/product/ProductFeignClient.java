package com.zq.client.product;

import com.example.spzx.model.dto.product.SkuSaleDto;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/18 16:21
 */

@FeignClient(value = "service-product")
public interface ProductFeignClient {
//远程调用skuId的值
    @GetMapping("/api/product/getBySkuId/{skuId}")
    ProductSku getBySkuId(@PathVariable("skuId") Long skuId);

    @PostMapping("/api/product/updateSkuSaleNum")
    Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList);

//返回一些写死的商品数据，用来暂时展示浏览记录

    @GetMapping("/user/product")
List<Product> get();



    }

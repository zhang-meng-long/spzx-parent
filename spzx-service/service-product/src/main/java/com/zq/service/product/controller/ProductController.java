package com.zq.service.product.controller;

import com.example.spzx.model.dto.h5.ProductSkuDto;
import com.example.spzx.model.dto.product.SkuSaleDto;
import com.example.spzx.model.entity.product.ProductSku;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.example.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;
import com.zq.service.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/16 21:28
 */
@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value="/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;


    @Operation(summary = "分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result<PageInfo<ProductSku>> findByPage(@Parameter(name = "page", description = "当前页码", required = true) @PathVariable Integer page,
                                                   @Parameter(name = "limit", description = "每页记录数", required = true) @PathVariable Integer limit,
                                                   @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false) ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result item(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo , ResultCodeEnum.SUCCESS);
    }


    @Operation(summary = "获取商品sku信息")
    @GetMapping("getBySkuId/{skuId}")
    public ProductSku getBySkuId(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
    }

//   订单完成后 更新商品销量
    @Operation(summary = "更新商品sku销量")
    @PostMapping("updateSkuSaleNum")
    public Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList) {
        return productService.updateSkuSaleNum(skuSaleDtoList);
    }

}

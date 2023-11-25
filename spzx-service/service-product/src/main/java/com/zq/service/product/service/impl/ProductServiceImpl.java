package com.zq.service.product.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spzx.model.dto.h5.ProductSkuDto;
import com.example.spzx.model.dto.product.SkuSaleDto;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductDetails;
import com.example.spzx.model.entity.product.ProductSku;
import com.example.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zq.service.product.config.Custom;
import com.zq.service.product.mapper.ProductDetailsMapper;
import com.zq.service.product.mapper.ProductMapper;

import com.zq.service.product.mapper.ProductSkuMapper;
import com.zq.service.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author 张乔
 * @Date 2023/11/14 14:50
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;



    @Cacheable(value ="category", key = "'sku'")
    @Override
    public List<ProductSku> selectTen() {
return productMapper.selectTen();
    }

    @Cacheable(value ="product", key = "'all'")
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
 List<ProductSku> productList= productMapper.findByPage(productSkuDto);
        return new PageInfo<>(productList);
    }

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Override
    public ProductItemVo item(Long skuId) {

        ProductSku productSku = productSkuMapper.selectById(skuId);

        Long productId = productSku.getProductId();

        Product product = productMapper.selectById(productId);

        List<ProductSku> skuList = productSkuMapper.selectList(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productId));
        //建立sku规格与skuId对应关系
        Map<String,Object> skuSpecValueMap = new HashMap<>();

skuList.forEach(it-> {
    skuSpecValueMap.put(it.getSkuSpec(),it.getId());
});

        ProductDetails productDetails = productDetailsMapper.selectOne(new LambdaQueryWrapper<ProductDetails>()
                .eq(ProductDetails::getProductId, productId));
        ProductItemVo productItemVo=new ProductItemVo();

        productItemVo.setProductSku(productSku);
        productItemVo.setProduct(product);


        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));

        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        return productItemVo;

    }

//    获取商品的sku信息
    @Override
    public ProductSku getBySkuId(Long skuId) {

        ProductSku productSku = productSkuMapper.selectById(skuId);

        return productSku;
    }


//   订单完成后 更新商品销量

    @Override
    public Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList) {

        if(!CollectionUtils.isEmpty(skuSaleDtoList)) {
            for(SkuSaleDto skuSaleDto : skuSaleDtoList) {
                ProductSku productSku = productSkuMapper.selectById(skuSaleDto.getSkuId());
//                销量相加
               productSku.setSaleNum(productSku.getSaleNum()+skuSaleDto.getNum());
//                库存相减
               productSku.setStockNum(productSku.getStockNum()-skuSaleDto.getNum());
                productSku.setUpdateTime(new Date());
               productSkuMapper.updateById(productSku);
            }
        }
        return true;

    }
}

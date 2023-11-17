package com.example.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.manager.config.PageInfoTest;
import com.example.manager.mapper.ProductDetailsMapper;
import com.example.manager.mapper.ProductMapper;
import com.example.manager.mapper.productSkuMapper;
import com.example.manager.service.ProductService;
import com.example.spzx.model.dto.product.ProductDto;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.entity.product.ProductDetails;
import com.example.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/12 15:17
 */
@Service
public class ProductServiceImpl implements ProductService {
@Autowired
private ProductMapper productMapper;


    //分页查询
    @Override
    public PageInfo<Product> getProductList(Integer page, Integer limit, ProductDto productDto) {

        PageHelper.startPage(page,limit);

          List<Product> productList= productMapper.selectLists(productDto);

PageInfo<Product> pageInfoList= new PageInfo<>(productList);

        return pageInfoList;
    }

    @Autowired
    private productSkuMapper productSkuMapper1;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;



    @Override
    public void save(Product product) {

        // 保存商品数据
        product.setStatus(0);              // 设置上架状态为0
        product.setAuditStatus(0);         // 设置审核状态为0
        productMapper.insert(product);

        // 保存商品sku数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i=0 ; i<productSkuList.size(); i++) {

            // 获取ProductSku对象
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode

            productSku.setProductId(product.getId());               // 设置商品id
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);                               // 设置销量
            productSku.setStatus(0);
            productSkuMapper1.insert(productSku);                    // 保存数据
        }
        // 保存商品详情数据
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.insert(productDetails);
    }

    @Override
    public Product getById(Long id) {
        // 根据id查询商品数据
        Product product = productMapper.selectByIds(id);

        // 根据商品的id查询sku数据
        List<ProductSku> productSkuList = productSkuMapper1.
                selectList(new LambdaQueryWrapper<ProductSku>()
                        .eq(ProductSku::getProductId, id)
                        .orderByDesc(ProductSku::getId));
        product.setProductSkuList(productSkuList);

        // 根据商品的id查询商品详情数据
        ProductDetails productDetails = productDetailsMapper.selectOne(
                new LambdaQueryWrapper<ProductDetails>().
                        eq(ProductDetails::getProductId, product.getId())
                        .orderByDesc(ProductDetails::getId)
               );
        product.setDetailsImageUrls(productDetails.getImageUrls());

        // 返回数据
        return product;
    }

    @Override
    public void updateById(Product product) {

        product.setUpdateTime(new Date());
        // 修改商品基本数据
        productMapper.updateById(product);

        // 修改商品的sku数据
        List<ProductSku> productSkuList = product.getProductSkuList();
        productSkuList.forEach(productSku -> {
            productSku.setUpdateTime(new Date());
            productSkuMapper1.updateById(productSku);
        });

        // 修改商品的详情数据
        ProductDetails productDetails = productDetailsMapper.selectOne(
                new LambdaQueryWrapper<ProductDetails>()
                        .eq(ProductDetails::getProductId, product.getId()));
        productDetails.setImageUrls(product.getDetailsImageUrls());
productDetails.setUpdateTime(new Date());
        productDetailsMapper.updateById(productDetails);

    }

    @Override
    public void deleteById(Long id) {
        productMapper.deleteById(id);                   // 根据id删除商品基本数据
        productSkuMapper1.delete(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId,id));         // 根据商品id删除商品的sku数据
        productDetailsMapper.delete(new LambdaQueryWrapper<ProductDetails>().eq(ProductDetails::getProductId,id));     // 根据商品的id删除商品的详情数据

    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }
}

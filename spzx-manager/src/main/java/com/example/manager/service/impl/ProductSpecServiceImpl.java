package com.example.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manager.config.PageInfoTest;
import com.example.manager.mapper.ProductSpecMapper;
import com.example.manager.service.ProductSpecService;
import com.example.spzx.model.entity.product.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/12 14:23
 */
@Service
public class ProductSpecServiceImpl implements ProductSpecService {
//    查询分页

    @Autowired
    private ProductSpecMapper mapper;


    @Override
    public PageInfoTest<ProductSpec> getList(Integer page, Integer limit) {

        IPage<ProductSpec> page1=new Page<>(page,limit);

        IPage<ProductSpec> productIPage = mapper.selectPage(page1,
                new LambdaQueryWrapper<ProductSpec>().orderByDesc(ProductSpec::getId));

        long total = productIPage.getTotal();
        List<ProductSpec> records = productIPage.getRecords();

        PageInfoTest<ProductSpec> pageInfoTest=new PageInfoTest(records,total);
        return pageInfoTest;
    }


    //新增
    @Override
    public void save(ProductSpec product) {

        mapper.insert(product);
    }

//    根据id修改
    @Override
    public void updateById(ProductSpec product) {
        product.setUpdateTime(new Date());
        mapper.updateById(product);
    }

    @Override
    public void deleteById(Integer id) {
//     根据id先查询出具体的数据，然后再进行update的修改，然后再进行逻辑删除
        ProductSpec product = mapper.selectById(id);
        product.setUpdateTime(new Date());
        mapper.updateById(product);

        mapper.deleteById(id);
    }

    @Override
    public List<ProductSpec> findAll() {

        List<ProductSpec> productSpecs = mapper.selectList(new LambdaQueryWrapper<ProductSpec>()
                .orderByDesc(ProductSpec::getId));

        return productSpecs;
    }


}

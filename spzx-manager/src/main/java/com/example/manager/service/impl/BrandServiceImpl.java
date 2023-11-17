package com.example.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.manager.config.PageInfoTest;
import com.example.manager.mapper.BrandMapper;
import com.example.manager.service.BrandService;
import com.example.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/7 15:37
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;



//    分压查询所有
    @Override
    public PageInfoTest<Brand> findByPage(Integer page, Integer limit) {

        IPage<Brand> page1=new Page<>(page,limit);
        LambdaQueryWrapper<Brand> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Brand::getId);
        IPage<Brand> brandIPage = brandMapper.selectPage(page1, lambdaQueryWrapper);
        List<Brand> records = brandIPage.getRecords();
        long total = brandIPage.getTotal();
        PageInfoTest<Brand> pageInfoTest=new PageInfoTest<>(records, total);
        return pageInfoTest;
    }
//新增
    @Override
    public void save(Brand brand) {

        brandMapper.insert(brand);


    }

    @Override
    public void deletedById(Long id) {
        brandMapper.deleteById(id);
    }

    @Override
    public void update(Brand brand) {
        brandMapper.updateById(brand);

    }

    @Override
    public List<Brand> findAll() {

        LambdaQueryWrapper<Brand> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Brand::getId);
        List<Brand> list=brandMapper.selectList(lambdaQueryWrapper);

        return list;
    }
}

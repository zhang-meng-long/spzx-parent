package com.example.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.manager.mapper.CategoryServiceMapper;
import com.example.manager.service.CategoryService;
import com.example.spzx.model.entity.product.Category;
import com.example.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.val;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/6 15:32
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryServiceMapper categoryServiceMapper;

    @Override
    public List<Category> findByParentId(Long parentId) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getParentId, parentId)

                .orderByDesc(Category::getId);
        List<Category> categoryList = categoryServiceMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(categoryList)) {

            if (!CollectionUtils.isEmpty(categoryList)) {

                // 遍历分类的集合，获取每一个分类数据
                categoryList.forEach(item -> {

                    Long aLong = categoryServiceMapper.selectCounts(item.getId());
                    // 查询该分类下子分类的数量
                    item.setHasChildren(aLong > 0);
                });
            }
        }


        return categoryList;
    }

//    导出功能
    @Override
    public void exportData(HttpServletResponse response) {

     try {
         // 设置响应结果类型
          response.setContentType("application/vnd.ms-excel");
                 response.setCharacterEncoding("utf-8");
         //
                 // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
                 String fileName = URLEncoder.encode("分类数据", "UTF-8");
                 response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
         List<Category> categoryList = categoryServiceMapper.selectList(new LambdaQueryWrapper<Category>().orderByAsc(Category::getId));
         List<CategoryExcelVo> categoryExcelVos=new ArrayList<>();

         for (Category category : categoryList){
             CategoryExcelVo categoryExcelVo=new CategoryExcelVo();
             BeanUtils.copyProperties(category,categoryExcelVo);
             categoryExcelVos.add(categoryExcelVo);
         }


         EasyExcel.write(response.getOutputStream(), Category.class).
                 sheet("分类数据").doWrite(categoryExcelVos);


     }catch (Exception e){
         e.printStackTrace();
         throw new RuntimeException("导出数据失败");
     }

    }
}
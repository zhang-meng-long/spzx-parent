package com.example.manager.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSON;
import com.example.spzx.model.entity.product.Category;
import com.example.spzx.model.vo.product.CategoryExcelVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/6 16:48
 */
@SpringBootTest
public class ExcelTest {

    private final ExcelListener<CategoryExcelVo> excelListener=new ExcelListener<>();



@Test
    void read(){
    String fileName = "D://01.xlsx";

//    解析excel表格
    ExcelReaderBuilder read = EasyExcel.read(fileName, CategoryExcelVo.class, excelListener);
    read.sheet().doRead();

    List<CategoryExcelVo> data = excelListener.getData();
    System.out.println(data);


}

@Test
    void wried(){

    List<CategoryExcelVo> list = new ArrayList<>() ;
    list.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
    list.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
    list.add(new CategoryExcelVo(11000L , "华为手机" , "",1L, 1, 2)) ;

EasyExcel.write("D://02.xlsx",CategoryExcelVo.class).sheet("一个简单的测试")
        .doWrite(list);
}

@Test
    void tets(){


    CategoryExcelVo categoryExcelVo = new CategoryExcelVo(1L, "数码办公", "", 100L, 1, 1);
    Category category=new Category();
    String toJSONString = JSON.toJSONString(categoryExcelVo);
    Category category1 = JSON.parseObject(toJSONString, Category.class);



    BeanUtils.copyProperties(categoryExcelVo,category);

    System.err.println(category);
    System.out.println(category1);
    System.out.println(categoryExcelVo);

}





}

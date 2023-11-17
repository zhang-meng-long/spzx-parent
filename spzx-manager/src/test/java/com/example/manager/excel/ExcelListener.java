package com.example.manager.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/6 16:42
 */
//@Component
public class ExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> data=new ArrayList<>();


    public List<T> getData(){
        return data;
    }
    //    从第二行开始读。将读取到的数据放入到泛型T中
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        data.add(t);
    }



//    在所有操作完成之后进行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

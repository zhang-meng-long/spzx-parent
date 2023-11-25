package com.zq.common.service.exception;

import com.example.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author 张乔
 * @Date 2023/10/31 18:15
 */
@RestControllerAdvice
public class GlobalException {

//    全局异常
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return Result.build(null,666,e.getMessage());



    }
//自定义异常
    @ExceptionHandler(CustomException.class)
    public Result customException(CustomException e){
        e.printStackTrace();
        return Result.build(null, e.getResultCodeEnum());


    }


}

package com.example.common.service.exception;

import com.example.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @Author 张乔
 * @Date 2023/10/31 18:18
 */
@Data
public class CustomException extends RuntimeException {
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public CustomException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.resultCodeEnum = resultCodeEnum;

    }





}

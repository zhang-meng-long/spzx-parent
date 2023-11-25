package com.example.manager.controller;

import com.example.manager.service.impl.FileUploadService;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.zq.miniospringbootstarter.config.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 张乔
 * @Date 2023/11/4 12:45
 */

@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

//    @Autowired
//    private FileUploadService fileUploadService;


    @Autowired
    private MinioService minioService;

    @PostMapping(value = "/fileUpload")
        public Result fileUpload(MultipartFile file){
     String url=  minioService.uploadImage("spzx",file);
     return Result.build(url, ResultCodeEnum.SUCCESS);
    }








}

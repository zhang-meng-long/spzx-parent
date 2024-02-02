package com.example.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.example.manager.config.MinioPojo;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

/**
 * @Author 张乔
 * @Date 2023/11/4 12:46
 */
@Service
public class FileUploadService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioPojo minioPojo;

    public String uploadImages(MultipartFile file) {
//        判断桶是否存在
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().
                    bucket(minioPojo.getBucket()).build());

            if (!bucketExists){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioPojo.getBucket()).build());
            }else {
                System.err.println("桶已经存在");
            }
//            发送文件
            String format = DateUtil.format(new Date(), "yyyy-MM-dd");

            String uuid= UUID.randomUUID().toString();

            String fileName=format+"/"+uuid+file.getOriginalFilename();



            minioClient.putObject(PutObjectArgs.builder()
                            .bucket(minioPojo.getBucket())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .object(fileName)
//                    加上类型时，访问时可以直接访问。不加类型时，访问时要先下载才能访问。
                                    .contentType(file.getContentType())
//                            .contentType("image/png")
                            .build());
            String url=minioPojo.getUrl()+"/"+minioPojo.getBucket()+"/"+fileName;
        return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败");
        }

    }
}

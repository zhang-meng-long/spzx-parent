package com.example.manager.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 张乔
 * @Date 2023/11/4 12:42
 */
@Configuration
public class MinioClientConfig {
@Autowired
    private MinioPojo minioPojo;
@Bean
    public MinioClient minioClient(){
    return MinioClient.builder()
            .endpoint(minioPojo.getUrl())
            .credentials(minioPojo.getUsername(),minioPojo.getPassword())
            .build();

}







}

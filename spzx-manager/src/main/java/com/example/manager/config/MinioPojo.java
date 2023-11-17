package com.example.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author 张乔
 * @Date 2023/11/4 12:40
 */
@Data
@ConfigurationProperties("spzx.minio")
@Component
public class MinioPojo {
   private String url;
  private String  username;
  private String  password;
   private String bucket;




}

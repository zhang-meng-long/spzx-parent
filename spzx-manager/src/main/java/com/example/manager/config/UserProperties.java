package com.example.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/1 20:21
 */
@Data
@ConfigurationProperties(prefix = "spzx.auth")
@Component
public class UserProperties {
    private List<String> noAuthUrls;

    
}

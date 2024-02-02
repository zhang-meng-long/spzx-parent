package com.zq.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 张乔
 * @Date 2023/11/28 15:21
 */
@Configuration
public class ESClientConfig {

    @Value("${elasticsearch.host}")
   private String host;

    @Value("${elasticsearch.port}")
  private   Integer port;

@Bean
    public RestHighLevelClient restHighLevelClient(){

    RestHighLevelClient restHighLevelClient=new RestHighLevelClient(
            RestClient.builder(new HttpHost(host, port)));
return restHighLevelClient;

}




}

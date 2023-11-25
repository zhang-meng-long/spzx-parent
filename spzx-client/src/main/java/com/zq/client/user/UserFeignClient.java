package com.zq.client.user;

import com.example.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author 张乔
 * @Date 2023/11/19 16:25
 */
@FeignClient(value = "service-user")
public interface UserFeignClient {

//    根据id查询用户地址
    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    UserAddress getUserAddress(@PathVariable Long id) ;






}

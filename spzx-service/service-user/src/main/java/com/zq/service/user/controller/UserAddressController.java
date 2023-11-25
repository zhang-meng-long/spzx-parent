package com.zq.service.user.controller;

import com.example.spzx.model.entity.user.UserAddress;
import com.example.spzx.model.vo.common.Result;
import com.zq.service.user.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/19 10:08
 */
@Tag(name = "用户地址接口")
@RestController
@RequestMapping(value="/api/user/userAddress")
public class UserAddressController {

@Autowired
private UserAddressService userAddressService;



    @Operation(summary = "获取用户地址列表")
    @GetMapping("auth/findUserAddressList")
    public Result findUserAddressList(@RequestHeader("token") String token) {
        List<UserAddress> userAddressList = userAddressService.findUserAddressList(token);
        return Result.SuccessData(userAddressList);

    }


    @Operation(summary = "获取地址信息")
    @GetMapping("getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id) {
        return userAddressService.getById(id);
    }





}

package com.zq.service.user.controller;

import com.example.spzx.model.dto.h5.UserLoginDto;
import com.example.spzx.model.dto.h5.UserRegisterDto;
import com.example.spzx.model.entity.product.Product;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.example.spzx.model.vo.h5.UserInfoVo;
import com.zq.service.user.service.RegistryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/17 20:38
 */
@RestController
@RequestMapping("api/user/userInfo")
public class RegisterController {
    @Autowired
    private RegistryService registryService;

//    用户浏览过的商品数据（我这里只做一个假商品数据）







//    注册
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto) {
        registryService.register(userRegisterDto);
        return Result.success();
    }


    @Operation(summary = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        String token = registryService.login(userLoginDto);
        return Result.SuccessData(token);
    }



    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("auth/getCurrentUserInfo")
    public Result  getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo= registryService.getUserInfo(token);
    return Result.SuccessData(userInfoVo);
    }





}

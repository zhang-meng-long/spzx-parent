package com.zq.service.user.service;

import com.example.spzx.model.dto.h5.UserLoginDto;
import com.example.spzx.model.dto.h5.UserRegisterDto;
import com.example.spzx.model.vo.h5.UserInfoVo;

/**
 * @Author 张乔
 * @Date 2023/11/17 20:43
 */

public interface RegistryService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getUserInfo(String token);
}

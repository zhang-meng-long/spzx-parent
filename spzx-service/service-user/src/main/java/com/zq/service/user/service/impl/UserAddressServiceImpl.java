package com.zq.service.user.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spzx.model.entity.user.UserAddress;
import com.example.spzx.model.entity.user.UserInfo;
import com.zq.service.user.mapper.UserAddressMapper;
import com.zq.service.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/19 10:08
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {

@Autowired
private RedisTemplate<String,String> redisTemplate;

@Autowired
private UserAddressMapper userAddressMapper;


//    获取用户地址列表
    @Override
    public List<UserAddress> findUserAddressList(String token) {

        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        List<UserAddress> userAddresses = userAddressMapper.selectList(new LambdaQueryWrapper<UserAddress>().
                eq(UserAddress::getUserId, userInfo.getId()));
        return userAddresses;
    }

    @Override
    public UserAddress getById(Long id) {

        return userAddressMapper.selectById(id);
    }

    @Override
    public void save(UserAddress userAddress) {
//        新增用户地址（用户已经登陆的情况下）
        userAddressMapper.insert(userAddress);
    }

}

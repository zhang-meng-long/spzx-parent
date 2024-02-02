package com.zq.service.user.service;

import com.example.spzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/19 10:08
 */

public interface UserAddressService {
    List<UserAddress> findUserAddressList(String token);

    UserAddress getById(Long id);

    void save(UserAddress userAddress);
}

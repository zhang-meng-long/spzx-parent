package com.zq.service.car.Service;

import com.example.spzx.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/18 15:56
 */

public interface CarService {
    void addToCart(Long skuId, Integer skuNum,String token);

    List<CartInfo> getList(String token);

    void deleteCart(String token, Long skuId);

    void checkCart(String token, Long skuId, Integer isChecked);

    void allCheckCart(String token, Integer isChecked);

    void clearCart(String token);

    List<CartInfo> getAllCkecked(String token);

    void deleteChecked(String token);
}

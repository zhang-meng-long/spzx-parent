package com.zq.service.car.Service.impl;

import com.alibaba.fastjson2.JSON;
import com.example.spzx.model.entity.h5.CartInfo;
import com.example.spzx.model.entity.product.ProductSku;
import com.example.spzx.model.entity.user.UserInfo;
import com.zq.client.product.ProductFeignClient;
import com.zq.service.car.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/18 15:56
 */
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
private ProductFeignClient productFeignClient;



    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

//    添加购物车
    @Override
    public void addToCart(Long skuId, Integer skuNum,String token) {
//        将购物车数据直接加到redis中，使用hash类型进行存储
//        1、得到当前登录的用户信息，得到token，查寻redis
        String redisDate = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisDate, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("请先登录");
        }
        String cartKey = getCartKey(userInfo.getId());
//        2、查看redis中，有没有存入同样的商品
        Object cartInfoObj  = redisTemplate.opsForHash().get(cartKey,String.valueOf(skuId));
        CartInfo cartInfo = null ;
        if(cartInfoObj != null) {       //  如果购物车中有该商品，则商品数量 相加！
            cartInfo = JSON.parseObject(cartInfoObj.toString() , CartInfo.class) ;
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }else {
            // 3、当购物车中没用该商品的时候，则直接添加到购物车！
            cartInfo = new CartInfo();
            // 远程调用，购物车数据是从商品详情得到 {skuInfo}
            ProductSku productSku = productFeignClient.getBySkuId(skuId) ;
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userInfo.getId());
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }


        //4、 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey , String.valueOf(skuId) , JSON.toJSONString(cartInfo));



    }

//    查询购物车中的数据
    @Override
    public List<CartInfo> getList(String token) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        Long userInfoId = userInfo.getId();
        String cartKey = getCartKey(userInfoId);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> infoList = objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).toList();
            System.err.println(infoList);
            return infoList ;
        }else {
            return new ArrayList<>();
        }

//        List<CartInfo> cartInfoList=new ArrayList<>();
//        将list中的Object做一个类型转换即可
//        for (Object object:objectList) {
//        CartInfo cartInfo=new CartInfo();
//            BeanUtils.copyProperties(object,cartInfo);
//            System.out.println(cartInfo);
//            cartInfoList.add(cartInfo);
//        }
//        return cartInfoList;
    }

    @Override
    public void deleteCart(String token, Long skuId) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        Long userInfoId = userInfo.getId();
        String cartKey = getCartKey(userInfoId);
//        删除redis中的数据
        redisTemplate.opsForHash().delete(cartKey,String.valueOf(skuId));

    }

//    更新选中状态
    @Override
    public void checkCart(String token, Long skuId, Integer isChecked) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        Long userInfoId = userInfo.getId();
        String cartKey = getCartKey(userInfoId);
        Object redisObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));
        if (redisObj==null){
            throw new RuntimeException("购物车中无此商品");
        }
//        更新状态
        CartInfo cartInfo = JSON.parseObject(redisObj.toString(), CartInfo.class);
        cartInfo.setIsChecked(isChecked);
//        重新放回redis中
        redisTemplate.opsForHash().put(cartKey,String.valueOf(skuId),JSON.toJSONString(cartInfo));
    }

//    全选或全不选
    @Override
    public void allCheckCart(String token, Integer isChecked) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        Long userInfoId = userInfo.getId();
        String cartKey = getCartKey(userInfoId);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);

        if (objectList.size() == 0){
            throw new RuntimeException("购物车中无商品");
        }

        List<CartInfo> cartInfoList = objectList.stream().map(obj -> JSON.parseObject(obj.toString(), CartInfo.class)).toList();

        for (CartInfo cartInfo : cartInfoList) {
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey,cartInfo.getSkuId().toString(),JSON.toJSONString(cartInfo));
        }

    }

//    删除购物车中所有的数据
    @Override
    public void clearCart(String token) {

        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        Long userInfoId = userInfo.getId();
        String cartKey = getCartKey(userInfoId);
        redisTemplate.delete(cartKey);


    }

//    选中的购物车
    @Override
    public List<CartInfo> getAllCkecked(String token) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        String cartKey = getCartKey(userInfo.getId());
        List<Object> valuesList = redisTemplate.opsForHash().values(cartKey);

        if (CollectionUtils.isEmpty(valuesList)){
            throw new RuntimeException("购物车为空");
        }

        List<CartInfo> objects = valuesList.stream().map(object ->
            JSON.parseObject(object.toString(), CartInfo.class)).toList();

        List<CartInfo> cartInfoList = objects.stream().
                filter(cartInfo -> cartInfo.getIsChecked() == 1).toList();

        return cartInfoList;
    }


//    删除已经生成订单的消息
    @Override
    public void deleteChecked(String token) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        String cartKey = getCartKey(userInfo.getId());
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        // 删除选中的购物项数据
        if(!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> cartInfoList = objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1).toList();
//            删除生成订单的购物车数据
            cartInfoList.forEach(cartInfo -> redisTemplate.opsForHash().
                    delete(cartKey , String.valueOf(cartInfo.getSkuId())));
        }

    }

}

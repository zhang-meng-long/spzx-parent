package com.zq.service.order.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spzx.model.dto.h5.OrderInfoDto;
import com.example.spzx.model.entity.h5.CartInfo;
import com.example.spzx.model.entity.order.OrderInfo;
import com.example.spzx.model.entity.order.OrderItem;
import com.example.spzx.model.entity.order.OrderLog;
import com.example.spzx.model.entity.product.ProductSku;
import com.example.spzx.model.entity.user.UserAddress;
import com.example.spzx.model.entity.user.UserInfo;
import com.example.spzx.model.vo.common.Result;
import com.example.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zq.client.cart.CartFeign;
import com.zq.client.product.ProductFeignClient;
import com.zq.client.user.UserFeignClient;
import com.zq.service.order.mapper.OrderInfoMapper;
import com.zq.service.order.mapper.OrderItemMapper;
import com.zq.service.order.mapper.OrderLogMapper;
import com.zq.service.order.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author 张乔
 * @Date 2023/11/19 12:22
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private CartFeign carFeign;

//    确认下单
    @Override
    public TradeVo getTrade(String token) {
        Result<List<CartInfo>> allCkecked = carFeign.getAllCkecked(token);
        List<CartInfo> cartInfoList = allCkecked.getData();
        // 获取选中的购物项列表数据
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {        // 将购物项数据转换成功订单明细数据
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        }

// 计算总金额
        BigDecimal totalAmount = new BigDecimal(0);
        for(OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        TradeVo tradeVo=new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        System.out.println(tradeVo);

        return tradeVo;
    }

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
@Autowired
private UserFeignClient userFeignClient;

@Autowired
private OrderItemMapper orderItemMapper;

@Autowired
private OrderLogMapper orderLogMapper;

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto,String token) {
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if (orderItemList == null || orderItemList.size() == 0){
            throw new RuntimeException("购物车中无数据");
        }
        for (OrderItem orderItem :orderItemList) {

            ProductSku bySkuId = productFeignClient.getBySkuId(orderItem.getSkuId());
            if(null == bySkuId) {
                throw new RuntimeException("购物车中无数据");
            }
            //校验库存
            if(orderItem.getSkuNum() > bySkuId.getStockNum()) {
                throw new RuntimeException("库存不足");
            }
        }
        String redisUser = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisUser, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }

        OrderInfo orderInfo = new OrderInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户id
        orderInfo.setUserId(userInfo.getId());
        //用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        //用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());

        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }

        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.insert(orderInfo);

        //保存订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.insert(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.insert(orderLog);
        // 5、清空购物车数据
        carFeign.deleteChecked(token);


        return orderInfo.getId();
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.selectById(orderId);
    }

//    立即购买
    @Override
    public TradeVo buy(Long skuId) {
        // 查询商品
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        // 计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);

        // 返回
        return tradeVo;
    }

    @Override
    public PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus, String token) {
        String redisData = redisTemplate.opsForValue().get(token);
        UserInfo userInfo = JSON.parseObject(redisData, UserInfo.class);
        if (userInfo==null){
            throw new RuntimeException("用户未登录");
        }
        LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderInfo::getUserId,userInfo.getId())
                .eq(orderStatus!=null,OrderInfo::getOrderStatus,orderStatus).
                orderByDesc(OrderInfo::getCreateTime);
        PageHelper.startPage(page,limit);
        List<OrderInfo> orderInfoList = orderInfoMapper.selectList(lambdaQueryWrapper);

        orderInfoList.forEach(orderInfo -> {
            List<OrderItem> orderItem = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>()
                    .eq(OrderItem::getOrderId,orderInfo.getId())
                    .orderByDesc(OrderItem::getId)
            );
            orderInfo.setOrderItemList(orderItem);
        });
        PageInfo<OrderInfo> pageInfo = new PageInfo<>(orderInfoList);


        return pageInfo;
    }

    @Override
    public OrderInfo getByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>().
                eq(!orderNo.isEmpty(), OrderInfo::getOrderNo, orderNo));
       if (orderInfo==null){
           throw new RuntimeException("订单不存在");
       }
        List<OrderItem> orderItems = orderItemMapper.selectList(new LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getOrderId, orderInfo.getId())
        );
       orderInfo.setOrderItemList(orderItems);
        return orderInfo;
    }


//    更新订单支付状态的接口供service-pay微服务进行调用

    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {

        // 更新订单状态
        OrderInfo orderInfo = orderInfoMapper.selectOne(new LambdaQueryWrapper<OrderInfo>()
                .eq(!orderNo.isEmpty(),OrderInfo::getOrderNo,orderNo)
        );
        orderInfo.setOrderStatus(1);
        orderInfo.setPayType(orderStatus);
        orderInfo.setPaymentTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        // 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLog.setUpdateTime(new Date());
        orderLogMapper.insert(orderLog);


    }


}





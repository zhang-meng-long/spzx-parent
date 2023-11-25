package com.zq.service.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.spzx.model.dto.product.SkuSaleDto;
import com.example.spzx.model.entity.order.OrderInfo;
import com.example.spzx.model.entity.order.OrderItem;
import com.example.spzx.model.entity.pay.PaymentInfo;
import com.zq.client.order.OrderFeignClient;
import com.zq.client.product.ProductFeignClient;
import com.zq.service.pay.mapper.PaymentInfoMapper;
import com.zq.service.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author 张乔
 * @Date 2023/11/20 15:00
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {


    @Autowired
    private PaymentInfoMapper paymentInfoMapper ;

    @Autowired
    private OrderFeignClient orderFeignClient ;

    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        // 查询支付信息数据，如果已经已经存在了就不用进行保存(一个订单支付失败以后可以继续支付)
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(new LambdaQueryWrapper<PaymentInfo>()
                .eq(!orderNo.isEmpty(),PaymentInfo::getOrderNo,orderNo)
        );
        if(null == paymentInfo) {
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo).getData();
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentInfoMapper.insert(paymentInfo);
        }
        return paymentInfo;
    }

    @Autowired
    private ProductFeignClient productFeignClient;


//    支付完成后，更改订单状态
    @Override
    public void updatePaymentStatus(Map<String, String> paramMap, int i) {
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(new LambdaQueryWrapper<PaymentInfo>()
                .eq(!paramMap.get("out_trade_no").isEmpty(),PaymentInfo::getOrderNo,paramMap.get("out_trade_no"))
        );
        if (paymentInfo.getPaymentStatus() == 1) {
            return;
        }

        //更新支付信息
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(paramMap.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(paramMap));
        paymentInfoMapper.updateById(paymentInfo);

        // 3、更新订单的支付状态
        orderFeignClient.updateOrderStatus(paymentInfo.getOrderNo() , i) ;



        // 4、更新商品销量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo()).getData();
        List<SkuSaleDto> skuSaleDtoList = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleDto skuSaleDto = new SkuSaleDto();
            skuSaleDto.setSkuId(item.getSkuId());
            skuSaleDto.setNum(item.getSkuNum());
            return skuSaleDto;
        }).collect(Collectors.toList());
        productFeignClient.updateSkuSaleNum(skuSaleDtoList) ;

    }
}

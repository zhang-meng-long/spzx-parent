package com.zq.service.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.example.spzx.model.entity.pay.PaymentInfo;
import com.example.spzx.model.vo.common.ResultCodeEnum;
import com.zq.service.pay.properpties.AlipayProperties;
import com.zq.service.pay.service.AlipayService;
import com.zq.service.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @Author 张乔
 * @Date 2023/11/20 15:31
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties ;

    //    支付宝下单
    @Override
    public String submitAlipay(String orderNo) {

        //保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no",paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
//        原资金数据
//        map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount",new BigDecimal("0.01"));
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        // 发送请求
        AlipayTradeWapPayResponse response = null;
        try {
            response = alipayClient.pageExecute(alipayRequest);
            if(response.isSuccess()){
                return response.getBody();
            } else {
                throw new RuntimeException("调用支付宝失败");
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }

    }

}

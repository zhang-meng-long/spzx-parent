package com.zq.service.pay.service;

import com.example.spzx.model.entity.pay.PaymentInfo;

import java.util.Map;

/**
 * @Author 张乔
 * @Date 2023/11/20 15:00
 */

public interface PaymentInfoService {

    PaymentInfo savePaymentInfo(String orderNo);

    void updatePaymentStatus(Map<String, String> paramMap, int i);
}

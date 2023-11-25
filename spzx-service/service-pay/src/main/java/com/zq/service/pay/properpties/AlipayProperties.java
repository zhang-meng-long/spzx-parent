package com.zq.service.pay.properpties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author 张乔
 * @Date 2023/11/20 14:59
 */

@Data
@ConfigurationProperties(prefix = "spzx.alipay")
@Component
public class AlipayProperties {

    private String alipayUrl;
    private String appPrivateKey;
    public  String alipayPublicKey;
    private String appId;
    public  String returnPaymentUrl;
    public  String notifyPaymentUrl;

    public final static String format="json";
    public final static String charset="utf-8";
    public final static String sign_type="RSA2";

}

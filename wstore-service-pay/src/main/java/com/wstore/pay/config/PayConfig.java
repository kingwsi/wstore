package com.wstore.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName PayConfig
 * @Author wangshu
 * @Date 18-10-23 上午8:23
 * @Version 1.0
 */
@Configuration
public class PayConfig {

    @Value("${pay.appid}")
    private String APP_ID;

    @Value("${pay.merchant_private_key}")
    private String MERCHANT_PRIVATE_KEY;

    @Value("${pay.alipay_public_key}")
    private String ALIPAY_PUBLIC_KEY;

    // 签名方式
    private final static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    private final static String CHARSET = "utf-8";

    @Value("${pay.host.gatway}")
    private String GATWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    @Value("${pay.host.notify}")
    private String NOTIFY_URL;

    @Value("${pay.host.return}")
    private String RETURN_URL;

    /**
     * 支付中心
     * @return
     */
    @Bean
    AlipayClient alipayClient() {
        return new DefaultAlipayClient(GATWAY_URL, APP_ID, MERCHANT_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }

    /**
     * 请求参数
     * @return
     */
    @Bean
    AlipayTradePagePayRequest payRequest(){
        AlipayTradePagePayRequest tradePagePayRequest = new AlipayTradePagePayRequest();
        tradePagePayRequest.setReturnUrl(RETURN_URL);
        tradePagePayRequest.setNotifyUrl(NOTIFY_URL);
        return tradePagePayRequest;
    }
}

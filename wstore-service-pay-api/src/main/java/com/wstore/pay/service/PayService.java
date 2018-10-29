package com.wstore.pay.service;

import com.alipay.api.AlipayApiException;
import com.wstore.pojo.pay.PaymentSlip;

import java.util.Date;
import java.util.Map;

public interface PayService {

    /**
     * 支付结果异步验签
     * @param params
     * @return
     */
    boolean notifySignVerify(Map<String, String> params);

    /**
     * 构建请求体
     *
     * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填
     * @param total_amount 付款金额，必填
     * @param subject      订单名称，必填
     * @param body         商品描述，可空
     * @return
     */
    String buildPayBody(String out_trade_no, String total_amount, String subject, String body) throws AlipayApiException;

    /**
     * 支付成功后生成支付单
     *
     * @param out_trade_no 商户单号
     * @param total_amount 支付金额 * 100
     * @param trade_no     支付中心流水号
     * @param timestamp    时间戳
     */
    PaymentSlip paymentSlip(Long out_trade_no, Integer total_amount, String trade_no, Date timestamp);
}

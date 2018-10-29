package com.wstore.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.wstore.mapper.OrderMapper;
import com.wstore.mapper.PaymentSlipMapper;
import com.wstore.pay.service.PayService;
import com.wstore.pojo.order.Order;
import com.wstore.pojo.order.OrderExample;
import com.wstore.pojo.order.OrderStatus;
import com.wstore.pojo.pay.PayChannel;
import com.wstore.pojo.pay.PaymentSlip;
import com.wstore.pojo.pay.PaymentSlipExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PayServiceImpl
 * @Author wangshu
 * @Date 18-10-23 上午8:29
 * @Version 1.0
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    AlipayClient alipayClient;

    @Autowired
    AlipayTradePagePayRequest alipayRequest;

    @Autowired
    PaymentSlipMapper paymentSlipMapper;

    @Autowired
    OrderMapper orderMapper;

    @Value("${pay.alipay_public_key}")
    private String ALIPAY_PUBLIC_KEY;

    /**
     * 支付结果异步验签
     *
     * @param params
     * @return
     */
    @Override
    public boolean notifySignVerify(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 构建请求体
     *
     * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填
     * @param total_amount 付款金额，必填
     * @param subject      订单名称，必填
     * @param body         商品描述，可空
     * @return
     */
    @Override
    public String buildPayBody(String out_trade_no, String total_amount, String subject, String body) throws AlipayApiException {
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"30m\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    /**
     * 支付成功后生成支付单
     *
     * @param out_trade_no 商户单号
     * @param total_amount 支付金额 * 100
     * @param timestamp    时间戳
     */
    @Transactional
    @Override
    public PaymentSlip paymentSlip(Long out_trade_no, Integer total_amount, String trade_no, Date timestamp) {
        PaymentSlipExample paymentSlipExample = new PaymentSlipExample();
        paymentSlipExample.createCriteria().andOrderSnEqualTo(out_trade_no);
        List<PaymentSlip> paymentSlips = paymentSlipMapper.selectByExample(paymentSlipExample);
        if (!paymentSlips.isEmpty()){
            return paymentSlips.get(0);
        }
        //创建支付单
        PaymentSlip paymentSlip = new PaymentSlip();
        paymentSlip.setOrderSn(out_trade_no);
        paymentSlip.setCreateTime(new Date());
        paymentSlip.setUpdateTime(new Date());
        paymentSlip.setTotalAmount(total_amount);
        paymentSlip.setTradeNo(trade_no);
        paymentSlip.setTimestamp(timestamp);
        paymentSlipMapper.insert(paymentSlip);

        //修改订单状态
        OrderExample orderExample = new OrderExample();
        Order order = new Order();
        order.setPayMoney(total_amount);
        order.setPayTime(timestamp);
        order.setPayChannel(PayChannel.ALI_PAY);
        order.setStatus(OrderStatus.WAIT_SEND);
        orderExample.createCriteria().andOrderSnEqualTo(out_trade_no);
        orderMapper.updateByExampleSelective(order, orderExample);

        return paymentSlip;
    }
}

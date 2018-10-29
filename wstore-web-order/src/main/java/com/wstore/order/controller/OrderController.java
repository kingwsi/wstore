package com.wstore.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.wstore.common.config.QueueEnum;
import com.wstore.common.utils.CookieUtils;
import com.wstore.common.utils.WstoreJsonUtil;
import com.wstore.common.utils.WstoreResultMsg;
import com.wstore.order.config.RedisUser;
import com.wstore.order.queue.OrderMessageConsumer;
import com.wstore.order.queue.OrderMessageProvicer;
import com.wstore.order.service.AddressService;
import com.wstore.order.service.OrderService;
import com.wstore.pay.service.PayService;
import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.order.Address;
import com.wstore.pojo.order.Order;
import com.wstore.pojo.order.OrderConfirm;
import com.wstore.pojo.pay.PaymentSlip;
import com.wstore.pojo.sso.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 订单请求处理
 *
 * @ClassName OrderController
 * @Author Koi
 * @Date 2018/9/18 10:35
 * @Version 1.0
 */

@CrossOrigin(origins = "http://localhost:8095", allowCredentials = "true", maxAge = 3600)
@Controller
public class OrderController {

    static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Reference
    OrderService orderService;

    @Reference
    AddressService addressService;

    @Autowired
    private OrderMessageProvicer messageProvider;

    @Reference
    PayService payService;

    @Autowired
    RedisUser redisToUser;

    @Value("${pay.result}")
    private String PAY_RESULT;

    @PostMapping("/order")
    public String toOrder(HttpServletRequest request, Map<String, Object> map, @RequestParam Integer[] carts) {
        User user = redisToUser.redisToUser(request);
        OrderConfirm orderConfirm = new OrderConfirm();
        orderConfirm.setCarts(carts);
        orderConfirm.setUserId(user.getUserId());
        //获取购物车信息
        List<ShoppingCart> items = orderService.getShoppingOrder(orderConfirm);
        map.put("items", items);
        //获取地址
        List<Address> addresses = addressService.getAddresses(user.getUserId());
        map.put("addresses", addresses);
        map.put("user", user);
        return "order";
    }

    @GetMapping("/rabbit")
    @ResponseBody
    public Object RabbitMqtest() {
        Order order = new Order();
        order.setLastPayTime(new Date());
        order.setOrderSn(Long.parseLong("1810231450755"));
        // 测试延迟10秒
        messageProvider.sendMessage(order,
                QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                10000);
        messageProvider.sendMessage("测试延迟消费2,写入时间：" + new Date(),
                QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                20000);
        return "SUCCESS";
    }

    /**
     * 提交订单
     *
     * @param order   表单
     * @param address 地址
     * @param request
     * @return
     */
    @PostMapping("/order/form")
    public String createOrder(Order order, Integer address, HttpServletRequest request, Map<String, Object> map) throws AlipayApiException {
        String uid = CookieUtils.getCookieValue(request, "UID");
        if (uid == null) {
            return "error";
        }
        String userJson = stringRedisTemplate.opsForValue().get(uid);
        if (userJson == null) {
            return "error";
        }
        User user = WstoreJsonUtil.fromJson(userJson, User.class);
        Order resultOrder = orderService.createOrder(order, address, user);
        resultOrder.getLastPayTime();

        //发送队列消息
        messageProvider.sendMessage(new Order(resultOrder.getOrderSn()),
                QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                resultOrder.getLastPayTime().getTime() - new Date().getTime());

        String payBody = payService.buildPayBody(resultOrder.getOrderSn().toString(),
                (resultOrder.getTotalMoney() / 100) + "",
                "Wstore商城订单",
                resultOrder.getDigest());
        map.put("data", payBody);
        return "pay";
    }

    /**
     * 支付结果
     * 同步返回
     *
     * @param out_trade_no 商户订单号
     * @param trade_no     支付中心流水号
     * @param total_amount 支付金额
     * @param timestamp    时间戳
     * @return
     */
    @GetMapping("/pay/result")
    public String payResult(@RequestParam Long out_trade_no,
                            @RequestParam String trade_no,
                            @RequestParam String total_amount,
                            @RequestParam String timestamp,
                            Map<String, Object> map) throws ParseException {
        int parseDouble = (int) (Double.parseDouble(total_amount) * 100);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        PaymentSlip paymentSlip = payService.paymentSlip(out_trade_no, Integer.valueOf(parseDouble), trade_no, simpleDateFormat.parse(timestamp));
        //自动收货
        if (paymentSlip == null) {
            return "redirect:/pay-result";
        }
        map.put("paymentSlip", paymentSlip);
        return "pay-result";
    }

    @PostMapping("/pay/notify")
    @ResponseBody
    public String payNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("收到支付结果异步请求");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        if (payService.notifySignVerify(params)) {
            if (orderService.verifyNotify(params)){
                response.getWriter().println("success");
                logger.info("支付订单支付成功（异步请求），订单{}",params.get("out_trade_no"));
            }
            logger.info("支付订单异常（异步请求），订单{}",params.get("out_trade_no"));
        }
        logger.info("请求参数异常（异步请求），订单{}",params.get("out_trade_no"));
        return null;
    }

    /**
     * 查看订单
     *
     * @param request
     * @return
     */
    @PostMapping("/order/center")
    @ResponseBody
    public WstoreResultMsg getOrder(HttpServletRequest request, @RequestParam Integer status) {
        User user = redisToUser.redisToUser(request);
        if (user == null) {
            return WstoreResultMsg.fail();
        }
        if (status < 0)
            status = null;
        List<Order> orders = orderService.getOrders(status, user);
        return WstoreResultMsg.success().add("orders", orders);
    }

    @GetMapping("/order/center/detail/{sn}")
    public String getOrderDetail(@PathVariable("sn") long sn) {
        return "order-center-detail";
    }


    @PostMapping("/test/center")
    @ResponseBody
    public WstoreResultMsg test(Integer status, HttpServletRequest request) {
        //User user = redisToUser.redisToUser(request);
        User user = new User();
        user.setUserId(13);
        if (user == null) {
            return WstoreResultMsg.fail();
        }
        List<Order> orders = orderService.getOrders(status, user);
        return WstoreResultMsg.success().add("orders", orders);
    }
}

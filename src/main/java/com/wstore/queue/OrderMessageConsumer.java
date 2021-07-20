package com.wstore.order.queue;

import com.wstore.pojo.order.Order;
import com.wstore.pojo.order.OrderStatus;
import com.wstore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单消息队列 consumer
 *
 * @ClassName OrderMessageConsumer
 * @Author wangshu
 * @Date 18-10-22 下午7:42
 * @Version 1.0
 */
@Component
public class OrderMessageConsumer {

    @Autowired
    OrderService orderService;

    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(OrderMessageConsumer.class);

    @RabbitListener(queues = "message.center.create")
    @RabbitHandler
    public void orderListener(Order order) {
        if (OrderStatus.WAIT_PAY == orderService.checkStatus(order.getOrderSn())) {
            //关闭交易
            logger.info("交易关闭：{}", order.getOrderSn());
            order.setStatus(OrderStatus.CLOSED);
            orderService.changeOrderStatus(order);
        }
    }
}

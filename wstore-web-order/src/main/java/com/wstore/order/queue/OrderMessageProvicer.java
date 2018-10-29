package com.wstore.order.queue;

import com.wstore.common.utils.WstoreJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @ClassName OrderMessageProvicer
 * @Author wangshu
 * @Date 18-10-22 下午11:08
 * @Version 1.0
 */
@Component
public class OrderMessageProvicer {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(OrderMessageProvicer.class);
    /**
     * RabbitMQ 模版消息实现类
     */
    @Autowired
    private AmqpTemplate rabbitMqTemplate;

    /**
     * 发送延迟消息
     *
     * @param messageContent 消息内容
     * @param exchange       队列交换
     * @param routerKey      队列交换绑定的路由键
     * @param delayTimes     延迟时长，单位：毫秒
     */
    public void sendMessage(Object messageContent, String exchange, String routerKey, final long delayTimes) {
        if (!StringUtils.isEmpty(exchange)) {
            logger.info("延迟：{}毫秒写入消息队列：{}，消息内容：{}", delayTimes, routerKey, WstoreJsonUtil.toJson(messageContent));
            // 执行发送消息到指定队列
            rabbitMqTemplate.convertAndSend(exchange, routerKey, messageContent, message -> {
                // 设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            });
        } else {
            logger.error("未找到队列消息：{}，所属的交换机", exchange);
        }
    }
}

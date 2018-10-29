package com.wstore.order;

import com.wstore.common.config.QueueEnum;
import com.wstore.order.queue.OrderMessageProvicer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WstoreWebOrderApplicationTests {

    /**
     * 消息队列提供者
     */
    @Autowired
    private OrderMessageProvicer messageProvider;

    /**
     * 测试延迟消息消费
     */
    @Test
    public void testLazy() {
        // 测试延迟10秒
        messageProvider.sendMessage("测试延迟消费,写入时间：" + new Date(),
                QueueEnum.MESSAGE_TTL_QUEUE.getExchange(),
                QueueEnum.MESSAGE_TTL_QUEUE.getRouteKey(),
                10000);
    }

    @Test
    public void dataTest() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //System.out.println(calendar.get(Calendar.DAY_OF_MONTH));//今天的日期
        //calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);//让日期加1
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 1);
        System.out.println("时间差：" + (calendar.getTime().getTime()-date.getTime()));
        long between = calendar.getTime().getTime()-date.getTime();
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                - min * 60 * 1000 - s * 1000);
        String timeDifference = day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
                + "毫秒";
        System.out.println(timeDifference);
    }
}

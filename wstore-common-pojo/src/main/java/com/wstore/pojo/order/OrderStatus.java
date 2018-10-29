package com.wstore.pojo.order;

/**
 * 订单状态码封装
 * @ClassName OrderStatus
 * @Author wangshu
 * @Date 18-10-8 上午8:15
 * @Version 1.0
 */
public class OrderStatus {

    //待付款
    public final static Integer WAIT_PAY = 0;

    //待发货
    public final static Integer WAIT_SEND = 1;

    //待收货
    public final static Integer WAIT_RECEIVE = 2;

    //已完成
    public final static Integer FINISHED = 3;

    //取消
    public final static Integer CANCEL = 4;

    //交易关闭
    public final static Integer CLOSED = 5;

    //删除
    public final static Integer DELETE = 6;
}

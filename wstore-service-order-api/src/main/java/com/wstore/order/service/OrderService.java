package com.wstore.order.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.order.Order;
import com.wstore.pojo.order.OrderConfirm;
import com.wstore.pojo.sso.User;

import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderService
 * @Author wangshu
 * @Date 18-9-30 上午9:11
 * @Version 1.0
 */
public interface OrderService {

    /**
     * 查询购物车信息，确认订单页面
     *
     * @param orderConfirm
     * @return
     */
    public List<ShoppingCart> getShoppingOrder(OrderConfirm orderConfirm);

    /**
     * 创建订单
     *
     * @param order
     * @param addressId 收货地址
     * @param user
     */
    Order createOrder(Order order, Integer addressId, User user);

    /**
     * 修改订单状态
     *
     * @param order
     */
    void changeOrderStatus(Order order);

    /**
     * 根据订单号查询订单状态
     *
     * @param orderSn
     * @return
     */
    Integer checkStatus(Long orderSn);

    /**
     * 根据订单状态查询订单
     *
     * @param orderStatus 状态码
     * @param user        用户
     * @return
     */
    List<Order> getOrders(Integer orderStatus, User user);

    /**
     * 异步返回结果，验证订单金额
     *
     * @param params
     * @return
     */
    boolean verifyNotify(Map<String, String> params);

}

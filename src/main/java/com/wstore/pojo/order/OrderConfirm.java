package com.wstore.pojo.order;

/**
 * 确认订单
 * @ClassName OrderConfirm
 * @Author wangshu
 * @Date 18-10-10 下午9:57
 * @Version 1.0
 */
public class OrderConfirm {

    private Integer userId;

    private Integer[] carts;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer[] getCarts() {
        return carts;
    }

    public void setCarts(Integer[] carts) {
        this.carts = carts;
    }

    public OrderConfirm(Integer userId, Integer[] cartList) {
        this.userId = userId;
        this.carts = cartList;
    }

    public OrderConfirm() {
    }
}

package com.wstore.pojo.cart;

import java.io.Serializable;
import java.util.List;

public class CartGroup implements Serializable {

    private static final long serialVersionUID = 1140398938470065825L;

    private Integer id;

    private Integer userId;

    private Integer groupId;

    private List<ShoppingCart> shoppingCarts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public CartGroup(Integer id, Integer userId, Integer groupId, List<ShoppingCart> shoppingCarts) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.shoppingCarts = shoppingCarts;
    }

    public CartGroup(Integer id, Integer userId, Integer groupId) {
        this.id = id;
        this.userId = userId;
        this.groupId = groupId;
    }

    public CartGroup() {
    }
}
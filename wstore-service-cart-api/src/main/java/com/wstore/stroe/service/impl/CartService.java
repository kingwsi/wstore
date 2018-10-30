package com.wstore.stroe.service.impl;

import com.wstore.pojo.cart.CartGroup;
import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.sso.User;

import java.util.List;

/**
 * @Author Koi
 * @Date 2018/9/3 10:35
 * @Version 1.0
 */
public interface CartService {
    /**
     * 查询商品信息
     *
     * @param skuId
     */
    ShoppingCart getProduct(Integer skuId);

    /**
     * 查询购物车数据并分组
     *
     * @param uid 用户id
     * @return 分组后的购物车信息
     */
    List<CartGroup> findShoppingCartToGroup(Integer uid);

    /**
     * 新增用户购物车信息
     *
     * @param cartGroups 分组后的购物车信息
     * @param userId     用户id
     */
    void addUserShoppingCart(List<CartGroup> cartGroups, Integer userId);

    /**
     * 添加商品到购物车
     *
     * @param cart
     */
    void addProductToCart(ShoppingCart cart);

    /**
     * 删除购物车商品
     *
     * @param shoppingCart
     */
    void deleteCartItemBySku(ShoppingCart shoppingCart);

    /**
     * 修改购物车商品数量
     *
     * @param amount
     * @param skuId
     * @param user
     */
    Integer updateCount(Integer amount, Integer skuId, User user);
}

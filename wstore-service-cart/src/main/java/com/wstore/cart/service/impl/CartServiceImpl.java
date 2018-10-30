package com.wstore.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wstore.mapper.CartGroupMapper;
import com.wstore.mapper.ProductMapper;
import com.wstore.mapper.ShoppingCartMapper;
import com.wstore.mapper.SkuMapper;
import com.wstore.pojo.cart.CartGroup;
import com.wstore.pojo.cart.CartGroupExample;
import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.cart.ShoppingCartExample;
import com.wstore.pojo.sso.User;
import com.wstore.stroe.service.impl.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName CartServiceImpl
 * @Author Koi
 * @Date 2018/8/28 9:06
 * @Version 1.0
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartGroupMapper cartGroupMapper;

    @Autowired
    SkuMapper skuMapper;

    /**
     * 查询商品信息
     *
     * @param skuId
     */
    @Override
    public ShoppingCart getProduct(Integer skuId) {
        ShoppingCart cart = shoppingCartMapper.selectBySku(skuId);
        return cart;
    }

    /**
     * 查询购物车数据并分组
     *
     * @param uid 用户id
     * @return 分组后的购物车信息
     */
    @Override
    public List<CartGroup> findShoppingCartToGroup(Integer uid) {
        return shoppingCartMapper.selectCartGroup(uid);
    }

    /**
     * 新增用户购物车信息
     *
     * @param cartGroups 分组后的购物车信息
     * @param userId     用户id
     */
    @Transactional
    @Override
    public void addUserShoppingCart(List<CartGroup> cartGroups, Integer userId) {

        //删除旧数据
        shoppingCartMapper.deleteGroupByUser(cartGroups);
        //插入group
        cartGroupMapper.insertCartGroups(cartGroups);
        for (CartGroup cartGroup : cartGroups) {
            shoppingCartMapper.insertShoppingCarts(cartGroup.getShoppingCarts());
        }
    }

    /**
     * 添加商品到购物车
     *
     * @param cart
     */
    @Transactional
    @Override
    public void addProductToCart(ShoppingCart cart) {
        ShoppingCartExample example = new ShoppingCartExample();
        example.createCriteria().andUserEqualTo(cart.getUser()).andSkuIdEqualTo(cart.getSkuId());
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByExample(example);
        cart.setUpdateTime(new Date());
        //该商品是否存在
        if (shoppingCarts.isEmpty()) {
            //sku信息不存在
            cart.setCreateTime(new Date());
            shoppingCartMapper.insertSelective(cart);

            //查询cart group是否存在 --不存在创建
            CartGroupExample cartGroupExample = new CartGroupExample();
            cartGroupExample.createCriteria().andUserIdEqualTo(cart.getUser()).andGroupIdEqualTo(cart.getBrandId());
            if (cartGroupMapper.selectByExample(cartGroupExample).isEmpty()) {
                cartGroupMapper.insertSelective(new CartGroup(null, cart.getUser(), cart.getBrandId()));
            }
        } else {
            //sku信息已存在
            int count = shoppingCarts.get(0).getCount() + cart.getCount();
            cart.setCount(count);
            cart.setUpdateTime(new Date());
            cart.setId(shoppingCarts.get(0).getId());
            shoppingCartMapper.updateByPrimaryKeySelective(cart);
        }
    }

    /**
     * 删除购物车商品
     *
     * @param shoppingCart
     */
    @Override
    @Transactional
    public void deleteCartItemBySku(ShoppingCart shoppingCart) {
        //删除购物车表
        ShoppingCartExample example = new ShoppingCartExample();
        example.createCriteria()
                .andSkuIdEqualTo(shoppingCart.getSkuId())
                .andUserEqualTo(shoppingCart.getUser());
        shoppingCartMapper.deleteByExample(example);
        //检查购物车中统一分组是否还存在sku,不存在就删除cart group
        ShoppingCartExample shoppingCartExample = new ShoppingCartExample();
        shoppingCartExample.createCriteria()
                .andUserEqualTo(shoppingCart.getUser())
                .andBrandIdEqualTo(shoppingCart.getBrandId());
        if (shoppingCartMapper.selectByExample(shoppingCartExample).isEmpty()) {
            CartGroupExample cartGroupExample = new CartGroupExample();
            cartGroupExample.createCriteria()
                    .andUserIdEqualTo(shoppingCart.getUser())
                    .andGroupIdEqualTo(shoppingCart.getBrandId());
            cartGroupMapper.deleteByExample(cartGroupExample);
        }
    }

    /**
     * 修改购物车商品数量
     *
     * @param amount
     * @param skuId
     * @param user
     */
    @Override
    public Integer updateCount(Integer amount, Integer skuId, User user) {
        ShoppingCartExample shoppingCartExample = new ShoppingCartExample();
        shoppingCartExample.createCriteria()
                .andUserEqualTo(user.getUserId())
                .andSkuIdEqualTo(skuId);
        //查询购物车原有商品
        ShoppingCart shoppingCart = shoppingCartMapper.selectByExample(shoppingCartExample).get(0);
        Integer frozenStock = skuMapper.selectByPrimaryKey(skuId).getFrozenStock();

        //数量是否合法
        if (amount <= frozenStock && amount > 0) {
            ShoppingCart shoppingCart1 = new ShoppingCart();
            shoppingCart1.setId(shoppingCart.getId());
            shoppingCart1.setCount(amount);
            shoppingCart1.setUpdateTime(new Date());
            shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart1);
            return frozenStock;
        }
        return -1;
    }
}

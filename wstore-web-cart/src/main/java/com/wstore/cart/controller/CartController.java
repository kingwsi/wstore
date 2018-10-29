package com.wstore.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wstore.common.utils.*;
import com.wstore.pojo.cart.CartGroup;
import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.sso.User;
import com.wstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CartController
 * @Author Koi
 * @Date 2018/9/4 16:27
 * @Version 1.0
 */
@CrossOrigin(origins = "http://localhost:8085", allowCredentials = "true", maxAge = 3600)
@Controller
public class CartController {

    @Value("${cart.temp.key}")
    private String CART_TEMP_KEY;

    @Value("${cart.temp.time}")
    private int CART_TEMP_TIME;

    //购物车商品分组（按品牌分组）
    @Value("${cart.group}")
    private String CART_GROUP;

    @Value("{pay.result}")
    private String PAY_RESULT;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Reference
    CartService cartService;

    /**
     * 获取购物车数据
     *
     * @param map
     * @param request
     * @return
     */
    @GetMapping("/cart")
    public String getCartGroup(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) {

        String cartId = CookieUtils.getCookieValue(request, CART_TEMP_KEY);
        String group_key = CART_GROUP + cartId;
        if (cartId == null) {
            cartId = WstoreRandomUtil.generateRandomString(32);
            CookieUtils.setCookie(request, response, CART_TEMP_KEY, cartId);
        }
        //是否登陆
        String userJson = stringRedisTemplate.opsForValue().get(cartId);
        if (userJson != null) {
            User user = WstoreJsonUtil.fromJson(userJson, User.class);
            //从数据库查出数据
            List<CartGroup> groups = cartService.findShoppingCartToGroup(user.getUserId());
            //缓存中是否有数据
            List redisGroups = redisTemplate.opsForHash().values(group_key);
            if (!redisGroups.isEmpty()) {
                redisTemplate.delete(group_key);
            }
            map.put("cartGroups", groups);
            map.put("user", user);
            return "cart";
        }
        List groups = redisTemplate.opsForHash().values(group_key);
        map.put("cartGroups", groups);
        return "cart";
    }

    /**
     * 添加商品到购物车功能
     *
     * @param id       sku
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/cart")
    @ResponseBody
    public WstoreResultMsg addProductToCart(@RequestParam Integer id,
                                            @RequestParam Integer count,
                                            HttpServletResponse response,
                                            HttpServletRequest request) {
        String cartId = CookieUtils.getCookieValue(request, CART_TEMP_KEY);
        if (cartId == null) {
            cartId = WstoreRandomUtil.generateRandomString(32);
            CookieUtils.setCookie(request, response, CART_TEMP_KEY, cartId);
        }
        //根据sku查询商品信息，组成购物车信息
        ShoppingCart cart = cartService.getProduct(id);
        if (cart == null) {
            return WstoreResultMsg.fail().add("error", "请求错误！请返回重试");
        }
        String redis_key = CART_TEMP_KEY + cartId;
        String group_key = CART_GROUP + cartId;
        cart.setCount(count);
        //登陆验证
        String userJson = stringRedisTemplate.opsForValue().get(cartId);
        User user = WstoreJsonUtil.fromJson(userJson, User.class);
        //是否登陆
        if (user != null) {
            //缓存中是否有数据
            List groups = redisTemplate.opsForHash().values(group_key);
            if (!groups.isEmpty()) {
                redisTemplate.delete(group_key);
            } else {
                cart.setUser(user.getUserId());
                cartService.addProductToCart(cart);
            }
            return WstoreResultMsg.success();
        }

        /*======== 未登陆用户缓存 =========*/
        //查询redis中是否存在该分组
        CartGroup cartGroup = (CartGroup) redisTemplate.opsForHash().get(group_key, cart.getBrandId());
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        if (cartGroup != null) {
            //获取-更新 缓存中的list
            shoppingCarts = cartGroup.getShoppingCarts();
            //查询是否已存在sku
            if (redisTemplate.opsForHash().get(redis_key, cart.getSkuId()) != null) {
                //循环遍历修改sku数量
                for (ShoppingCart shoppingCart : shoppingCarts) {
                    if (cart.getSkuId().equals(shoppingCart.getSkuId())) {
                        shoppingCart.setCreateTime(null);
                        shoppingCart.setUpdateTime(new Date());
                        shoppingCart.setCount(shoppingCart.getCount() + cart.getCount());
                    }
                }
            } else {
                //不存在sku直接添加
                shoppingCarts.add(cart);
            }
        } else {
            //不存在的分组，直接添加分组
            cart.setUpdateTime(new Date());
            cart.setCreateTime(new Date());
            shoppingCarts.add(cart);
        }

        //查询redis中是否已存在该商品
        if (redisTemplate.opsForHash().get(redis_key, cart.getSkuId()) == null) {
            //设置有效期
            redisTemplate.expire(redis_key, CART_TEMP_TIME, TimeUnit.MINUTES);
            redisTemplate.opsForHash().put(redis_key, cart.getSkuId(), cart.getSkuId());
        }

        //以分组形式向redis中存入购物车信息
        cartGroup = new CartGroup(null, cart.getUser(), cart.getBrandId(), shoppingCarts);
        redisTemplate.opsForHash().put(group_key, cartGroup.getGroupId(), cartGroup);
        return WstoreResultMsg.success();
    }

    /**
     * 根据sku删除购物车商品
     *
     * @return
     */
    @DeleteMapping("/cart/{group}/{sku}")
    @ResponseBody
    public WstoreResultMsg deleteCart(@PathVariable("sku") Integer sku, @PathVariable("group") Integer group, HttpServletRequest request) {
        //获取用户cartId
        String cartId = CookieUtils.getCookieValue(request, CART_TEMP_KEY);
        if (cartId == null) {
            return WstoreResultMsg.fail().add("error", "身份校验失败！");
        }

        String userJson = stringRedisTemplate.opsForValue().get(cartId);
        User user = WstoreJsonUtil.fromJson(userJson, User.class);
        if (user != null) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user.getUserId());
            shoppingCart.setBrandId(group);
            shoppingCart.setSkuId(sku);
            cartService.deleteCartItemBySku(shoppingCart);
            return WstoreResultMsg.success();
        }

        String group_key = CART_GROUP + cartId;
        CartGroup cartGroup = (CartGroup) redisTemplate.opsForHash().get(group_key, group);
        List<ShoppingCart> shoppingCarts = cartGroup.getShoppingCarts();
        //使用迭代器删除
        Iterator<ShoppingCart> iterator = shoppingCarts.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getSkuId().equals(sku)) {
                iterator.remove();
            }
        }
        cartGroup.setShoppingCarts(shoppingCarts);
        //删除旧数据
        redisTemplate.opsForHash().delete(group_key, group);
        //添加新数据
        if (!shoppingCarts.isEmpty()) {
            redisTemplate.opsForHash().put(group_key, group, cartGroup);
        }
        return WstoreResultMsg.success();
    }

    /**
     * 购物车商品数量修改
     *
     * @param request
     * @param skuId
     * @param amount
     * @return
     */
    @PostMapping("/cart/{sku}/{amount}")
    @ResponseBody
    public WstoreResultMsg updateItemCount(HttpServletRequest request, @PathVariable("sku") Integer skuId, @PathVariable("amount") Integer amount) {
        User user = WstoreJsonUtil.fromJson(stringRedisTemplate.opsForValue()
                .get(CookieUtils.getCookieValue(request, CART_TEMP_KEY)), User.class);
        if (user == null){
            return WstoreResultMsg.fail().add("error","请先登录！");
        }
        Integer result = cartService.updateCount(amount, skuId, user);
        if (result > 0) {
            return WstoreResultMsg.success().add("frozenStock",result);
        }
        return WstoreResultMsg.fail();
    }

}

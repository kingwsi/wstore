package com.wstore.service;

import org.springframework.stereotype.Service;
import com.wstore.mapper.*;
import com.wstore.pojo.admin.Sku;
import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.order.*;
import com.wstore.pojo.sso.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import com.wstore.common.utils.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderServiceImpl
 * @Author wangshu
 * @Date 18-10-7 下午9:01
 * @Version 1.0
 */
@Service
@Transactional
public class OrderService {

    protected static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    OrderAddressMapper orderAddressMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    SkuMapper skuMapper;

    private final static Integer LAST_PAY = 30;

    private final static Integer LAST_SHIP = 7;

    /**
     * 查询购物车信息，确认订单页面
     *
     * @param orderConfirm
     * @return
     */

    public List<ShoppingCart> getShoppingOrder(OrderConfirm orderConfirm) {
        return orderMapper.selectCartToOrder(orderConfirm);
    }

    /**
     * 创建订单
     *
     * @param order
     * @param addressId
     * @param user
     */

    public Order createOrder(Order order, Integer addressId, User user) {
        //创建订单信息
        Long sn = WstoreRandomUtil.genOrderSn();
        order.setOrderSn(sn);
        order.setStatus(OrderStatus.WAIT_PAY);
        order.setUserId(user.getUserId());
        order.setTotalMoney(0);
        order.setDigest("");
        order.setCommentStatus(0);
        //订单创建时间
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        order.setCreateTime(date);
        //支付截止时间
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + LAST_PAY);
        order.setLastPayTime(calendar.getTime());
        //确认收货时间
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + LAST_SHIP);
        order.setLastComfirmShipTime(calendar.getTime());
        logger.info("创建订单：" + sn);

        //------生成订单地址---
        //查询地址
        OrderAddress orderAddress = orderAddressMapper.selectFromAddressTable(addressId);
        //插入地址
        orderAddress.setOrderSn(sn);
        orderAddress.setCreateTime(new Date());
        orderAddress.setUpdateTime(new Date());
        orderAddressMapper.insertSelective(orderAddress);
        //------订单明细------
        //遍历查询购物车商品
        for (Integer cartId : order.getCartList()) {
            OrderDetail orderDetail = new OrderDetail();
            ShoppingCart shoppingCarts = shoppingCartMapper.selectByPrimaryKey(cartId);
            //检查是否为当前用户cart数据
            if (shoppingCarts.getUser() != user.getUserId()) return null;
            //从购物车查询sku
            Sku sku = skuMapper.selectByPrimaryKey(shoppingCarts.getSkuId());
            //计算价格
            order.setTotalMoney(order.getTotalMoney() + sku.getPrice() * shoppingCarts.getCount());
            order.setDigest(order.getDigest() + " " + shoppingCarts.getProductName());
            //构建订单明细
            orderDetail.setCreateTime(new Date());
            orderDetail.setUpdateTime(new Date());
            orderDetail.setOrderSn(sn);
            orderDetail.setProductId(shoppingCarts.getProductId());
            orderDetail.setProductName(shoppingCarts.getProductName());
            orderDetail.setPrice(sku.getPrice());
            orderDetail.setSkuId(sku.getId());
            orderDetail.setSkuPic(sku.getSkuPic());
            orderDetail.setSkuProperties(shoppingCarts.getSkuParam());
            orderDetail.setMarketPrice(sku.getMarketPrice());
            orderDetail.setAmount(shoppingCarts.getCount());
            orderDetail.setTotalMoney(sku.getPrice() * shoppingCarts.getCount());
            orderDetailMapper.insertSelective(orderDetail);
        }
        logger.info("创建订单：{}", sn);
        orderMapper.insertSelective(order);
        return order;
    }

    /**
     * 根据orderSn修改订单状态
     *
     * @param order
     */

    public void changeOrderStatus(Order order) {
        order.setUpdateTime(new Date());
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderSnEqualTo(order.getOrderSn());
        orderMapper.updateByExampleSelective(order, orderExample);
    }

    /**
     * 根据订单号查询订单状态
     *
     * @param orderSn
     * @return
     */

    public Integer checkStatus(Long orderSn) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderSnEqualTo(orderSn);
        List<Order> orders = orderMapper.selectByExample(orderExample);
        if (orders.isEmpty()) {
            return -1;
        }
        return orders.get(0).getStatus();
    }

    /**
     * 根据订单状态查询订单
     *
     * @param orderStatus
     * @return
     */

    public List<Order> getOrders(Integer orderStatus, User user) {
        return orderMapper.selectOrderWithDetail(user.getUserId(), orderStatus);
    }

    /**
     * 异步返回结果，验证订单金额
     *
     * @param params
     * @return
     */

    public boolean verifyNotify(Map<String, String> params) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderSnEqualTo(Long.parseLong(params.get("out_trade_no")));
        List<Order> orders = orderMapper.selectByExample(orderExample);
        if (orders.isEmpty()) {
            return false;
        }
        Integer total_amount = Integer.parseInt(params.get("total_amount").replace(".", ""));
        if (orders.get(0).getTotalMoney() != total_amount){
            return false;
        }
        //校验通过订单处理
        Order order = new Order();
        order.setOrderSn(Long.parseLong(params.get("out_trade_no")));
        order.setStatus(OrderStatus.WAIT_SEND);
        this.changeOrderStatus(order);
        return true;
    }
}

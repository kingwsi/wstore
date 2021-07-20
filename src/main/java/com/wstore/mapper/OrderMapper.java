package com.wstore.mapper;

import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.order.Order;
import com.wstore.pojo.order.OrderConfirm;
import com.wstore.pojo.order.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    List<Order> selectOrderWithDetail(@Param("userId") Integer userId,@Param("status") Integer status);

    Order selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //订单确认页面
    List<ShoppingCart> selectCartToOrder(@Param("orderConfirm") OrderConfirm orderConfirm);
}
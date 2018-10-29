package com.wstore.mapper;

import java.util.List;

import com.wstore.pojo.cart.CartGroup;
import com.wstore.pojo.cart.ShoppingCart;
import com.wstore.pojo.cart.ShoppingCartExample;
import org.apache.ibatis.annotations.Param;

public interface ShoppingCartMapper {
    long countByExample(ShoppingCartExample example);

    int deleteByExample(ShoppingCartExample example);

    int deleteGroupByUser(List<CartGroup> groups);

    int deleteByPrimaryKey(Integer id);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    int insertShoppingCarts(List<ShoppingCart> shoppingCarts);

    List<ShoppingCart> selectByExample(ShoppingCartExample example);

    List<CartGroup> selectCartGroup(Integer id);

    ShoppingCart selectBySku(Integer id);

    ShoppingCart selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    int updateByExample(@Param("record") ShoppingCart record, @Param("example") ShoppingCartExample example);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);

}
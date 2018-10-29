package com.wstore.mapper;

import java.util.List;

import com.wstore.pojo.cart.CartGroup;
import com.wstore.pojo.cart.CartGroupExample;
import org.apache.ibatis.annotations.Param;

public interface CartGroupMapper {
    long countByExample(CartGroupExample example);

    int deleteByExample(CartGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CartGroup record);

    int insertSelective(CartGroup record);

    int insertCartGroups(List<CartGroup> cartGroups);

    List<CartGroup> selectByExample(CartGroupExample example);

    CartGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CartGroup record, @Param("example") CartGroupExample example);

    int updateByExample(@Param("record") CartGroup record, @Param("example") CartGroupExample example);

    int updateByPrimaryKeySelective(CartGroup record);

    int updateByPrimaryKey(CartGroup record);
}
package com.wstore.mapper;

import com.wstore.pojo.order.OrderAddress;
import com.wstore.pojo.order.OrderAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderAddressMapper {
    long countByExample(OrderAddressExample example);

    int deleteByExample(OrderAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderAddress record);

    int insertSelective(OrderAddress record);

    List<OrderAddress> selectByExample(OrderAddressExample example);

    OrderAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderAddress record, @Param("example") OrderAddressExample example);

    int updateByExample(@Param("record") OrderAddress record, @Param("example") OrderAddressExample example);

    int updateByPrimaryKeySelective(OrderAddress record);

    int updateByPrimaryKey(OrderAddress record);

    //从address表查询
    OrderAddress selectFromAddressTable(Integer id);
}
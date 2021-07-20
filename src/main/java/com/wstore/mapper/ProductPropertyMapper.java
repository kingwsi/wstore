package com.wstore.mapper;

import com.wstore.pojo.admin.ProductProperty;
import com.wstore.pojo.admin.ProductPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductPropertyMapper {
    long countByExample(ProductPropertyExample example);

    int deleteByExample(ProductPropertyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductProperty record);

    int insertSelective(ProductProperty record);

    List<ProductProperty> selectByExample(ProductPropertyExample example);

    ProductProperty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductProperty record, @Param("example") ProductPropertyExample example);

    int updateByExample(@Param("record") ProductProperty record, @Param("example") ProductPropertyExample example);

    int updateByPrimaryKeySelective(ProductProperty record);

    int updateByPrimaryKey(ProductProperty record);
}
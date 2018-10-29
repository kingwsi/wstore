package com.wstore.mapper;

import com.wstore.pojo.admin.SkuProperty;
import com.wstore.pojo.admin.SkuPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SkuPropertyMapper {
    long countByExample(SkuPropertyExample example);

    int deleteByExample(SkuPropertyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SkuProperty record);

    int insertSelective(SkuProperty record);

    List<SkuProperty> selectByExample(SkuPropertyExample example);

    SkuProperty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SkuProperty record, @Param("example") SkuPropertyExample example);

    int updateByExample(@Param("record") SkuProperty record, @Param("example") SkuPropertyExample example);

    int updateByPrimaryKeySelective(SkuProperty record);

    int updateByPrimaryKey(SkuProperty record);
}
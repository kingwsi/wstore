package com.wstore.mapper;

import com.wstore.pojo.admin.CommonProperty;
import com.wstore.pojo.admin.CommonPropertyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommonPropertyMapper {
    long countByExample(CommonPropertyExample example);

    int deleteByExample(CommonPropertyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommonProperty record);

    int insertSelective(CommonProperty record);

    List<CommonProperty> selectByExample(CommonPropertyExample example);

    //只查询属性集合
    List<String> selectPropertiesByCid(Integer categoryId);

    CommonProperty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommonProperty record, @Param("example") CommonPropertyExample example);

    int updateByExample(@Param("record") CommonProperty record, @Param("example") CommonPropertyExample example);

    int updateByPrimaryKeySelective(CommonProperty record);

    int updateByPrimaryKey(CommonProperty record);
}
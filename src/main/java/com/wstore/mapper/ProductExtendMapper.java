package com.wstore.mapper;

import com.wstore.pojo.admin.ProductExtend;
import com.wstore.pojo.admin.ProductExtendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductExtendMapper {
    long countByExample(ProductExtendExample example);

    int deleteByExample(ProductExtendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductExtend record);

    int insertSelective(ProductExtend record);

    List<ProductExtend> selectByExampleWithBLOBs(ProductExtendExample example);

    List<ProductExtend> selectByExample(ProductExtendExample example);

    ProductExtend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductExtend record, @Param("example") ProductExtendExample example);

    int updateByExampleWithBLOBs(@Param("record") ProductExtend record, @Param("example") ProductExtendExample example);

    int updateByExample(@Param("record") ProductExtend record, @Param("example") ProductExtendExample example);

    int updateByPrimaryKeySelective(ProductExtend record);

    int updateByPrimaryKeyWithBLOBs(ProductExtend record);

    int updateByPrimaryKey(ProductExtend record);
}
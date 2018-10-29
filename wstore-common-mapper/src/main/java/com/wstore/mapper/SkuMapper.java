package com.wstore.mapper;

import com.wstore.pojo.admin.Product;
import com.wstore.pojo.admin.Sku;
import com.wstore.pojo.admin.SkuExample;
import java.util.List;
import java.util.Set;

import com.wstore.pojo.item.SkuAllProp;
import com.wstore.pojo.item.SkuPolymerization;
import org.apache.ibatis.annotations.Param;

public interface SkuMapper {
    long countByExample(SkuExample example);

    int deleteByExample(SkuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sku record);

    int insertSelective(Sku record);

    List<Sku> selectByExample(SkuExample example);

    List<Sku> selectWithPropertyByCode(String productCode);

    List<SkuAllProp> selectAllProperties(String code);

    //sku聚合查询
    List<SkuPolymerization> selectSkuPolymerizationByCode(String code);

    Product findMaxAndMinPrice(String productCode);

    Sku selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByExample(@Param("record") Sku record, @Param("example") SkuExample example);

    int updateByPrimaryKeySelective(Sku record);

    int updateByPrimaryKey(Sku record);
}
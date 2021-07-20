package com.wstore.mapper;

import com.wstore.pojo.admin.Product;
import com.wstore.pojo.admin.ProductExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ProductMapper {
    long countByExample(ProductExample example);

    int deleteByExample(ProductExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    List<Product> selectWithCategory(@Param("startRow") Integer startRow, @Param("endRow") Integer endRow);

    int batchOnSale();

    Product selectByPrimaryKey(Long id);

    Product WithAllByPrimaryKey(Long id);

    //根据商品code查询对应属性集
    Product selectPropertiesByCode(String code);

    //查询分类下的商品
    List<Product> selectByCategory(Integer categoryId);

    //最高/低价格查询
    Product findMaxAndMinPrice(String productCode);

    //查询商品sku数量
    int findSkuCount(String productCode);

    int deleteWithAllByCode(String code);

    int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}
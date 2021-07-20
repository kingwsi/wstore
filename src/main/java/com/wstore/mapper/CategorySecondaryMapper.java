package com.wstore.mapper;

import com.wstore.pojo.admin.CategorySecondary;
import com.wstore.pojo.admin.CategorySecondaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategorySecondaryMapper {
    long countByExample(CategorySecondaryExample example);

    int deleteByExample(CategorySecondaryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CategorySecondary record);

    int insertSelective(CategorySecondary record);

    List<CategorySecondary> selectByExample(CategorySecondaryExample example);

    List<CategorySecondary> selectByExampleWithCategory(CategorySecondaryExample example);

    CategorySecondary selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CategorySecondary record, @Param("example") CategorySecondaryExample example);

    int updateByExample(@Param("record") CategorySecondary record, @Param("example") CategorySecondaryExample example);

    int updateByPrimaryKeySelective(CategorySecondary record);

    int updateByPrimaryKey(CategorySecondary record);
}
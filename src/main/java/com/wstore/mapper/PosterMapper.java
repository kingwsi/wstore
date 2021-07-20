package com.wstore.mapper;

import com.wstore.pojo.admin.Poster;
import com.wstore.pojo.admin.PosterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PosterMapper {
    long countByExample(PosterExample example);

    int deleteByExample(PosterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Poster record);

    int insertSelective(Poster record);

    List<Poster> selectByExample(PosterExample example);

    Poster selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Poster record, @Param("example") PosterExample example);

    int updateByExample(@Param("record") Poster record, @Param("example") PosterExample example);

    int updateByPrimaryKeySelective(Poster record);

    int updateByPrimaryKey(Poster record);
}
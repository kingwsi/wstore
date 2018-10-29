package com.wstore.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wstore.mapper.BrandMapper;
import com.wstore.mapper.CategoryMapper;
import com.wstore.pojo.admin.Brand;
import com.wstore.pojo.admin.BrandExample;
import com.wstore.pojo.admin.Category;
import com.wstore.pojo.admin.CategoryExample;
import com.wstore.search.service.ListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName ListServiceImpl
 * @Author Koi
 * @Date 2018/8/16 16:53
 * @Version 1.0
 */
@Service
public class ListServiceImpl implements ListService {

    @Autowired
    BrandMapper brandMapper;

    @Autowired
    CategoryMapper categoryMapper;
    /**
     * 获取品牌列表
     *
     * @return
     */
    @Override
    public List<Brand> getBrands() {
        BrandExample example = new BrandExample();
        example.createCriteria().andStatusEqualTo(1);
        return brandMapper.selectPart(example);
    }

    /**
     * 获取分类列表
     *
     * @return
     */
    @Override
    public List<Category> getCategories() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andStatusEqualTo(1);
        return categoryMapper.selectByExample(example);
    }
}

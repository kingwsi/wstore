package com.wstore.admin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wstore.admin.service.CategoryService;
import com.wstore.mapper.CategoryMapper;
import com.wstore.mapper.CategorySecondaryMapper;
import com.wstore.mapper.CommonPropertyMapper;
import com.wstore.pojo.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Author Koi
 * @Date 2018/7/29 15:36
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategorySecondaryMapper categorySecondaryMapper;

    @Autowired
    CommonPropertyMapper commonPropertyMapper;

    /**
     * 获取所有一级分类
     *
     * @return
     */
    @Override
    public List<Category> getAllCategory() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("sort DESC");
        return categoryMapper.selectByExample(example);
    }

    /**
     * 新添加一级分类
     *
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        categoryMapper.insertSelective(category);
    }

    /**
     * 新增二级分类
     *
     * @param categorySecondary
     */
    @Override
    public void addCategory(CategorySecondary categorySecondary) {
        categorySecondaryMapper.insertSelective(categorySecondary);
    }

    /**
     * 修改一级分类
     *
     * @param category
     */
    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    /**
     * 修改二级分类
     *
     * @param categorySecondary
     */
    @Override
    public void updateCategory(CategorySecondary categorySecondary) {
        categorySecondaryMapper.updateByPrimaryKeySelective(categorySecondary);
    }

    /**
     * 获取所有二级分类
     * @return
     */
    @Override
    public List<CategorySecondary> getAllCategorySecondary() {
        return categorySecondaryMapper.selectByExampleWithCategory(null);
    }

    /**
     * 获取所有分类名
     * @return
     */
    @Override
    public List<Category> getCategoryNameList() {
        return categoryMapper.selectNameWithSecondary(null);
    }

    /**
     * 获取所有分类对应的属性集
     *
     * @return 包含分类属性的Category集合
     */
    @Override
    public Category getCategoryProperties(Integer id) {
        return categoryMapper.selectCategoryProperties(id);
    }

    /**
     * 查询分类下的属性集
     *
     * @param categoryId 分类id
     * @return
     */
    @Override
    public List<String> getAllProperty(Integer categoryId) {
        return commonPropertyMapper.selectPropertiesByCid(categoryId);
    }

    /**
     * 更新属性集
     *
     * @param properties 属性集合
     * @param categoryId 分类id
     */
    @Transactional
    @Override
    public void updateProperty(List<String> properties, Integer categoryId) {

        //清理旧属性
        CommonPropertyExample example = new CommonPropertyExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        commonPropertyMapper.deleteByExample(example);
        //添加新属性
        CommonProperty commonProperty = new CommonProperty();
        commonProperty.setCategoryId(categoryId);
        for (String property:properties){
            commonProperty.setName(property);
            commonPropertyMapper.insert(commonProperty);
        }
    }
}

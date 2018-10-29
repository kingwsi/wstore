package com.wstore.admin.service;

import com.wstore.pojo.admin.Category;
import com.wstore.pojo.admin.CategorySecondary;
import com.wstore.pojo.admin.CommonProperty;

import java.util.List;
import java.util.Map;

/**
 * 分类服务
 *
 * @ClassName CategoryService
 * @Author Koi
 * @Date 2018/7/29 15:34
 * @Version 1.0
 */
public interface CategoryService {

    /**
     * 获取所有一级分类
     * @return
     */
    public List<Category> getAllCategory();

    /**
     * 新添加一级分类
     * @param category
     */
    void addCategory(Category category);

    /**
     * 新增二级分类
     * @param categorySecondary
     */
    void addCategory(CategorySecondary categorySecondary);

    /**
     * 修改一级分类
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 修改二级分类
     * @param categorySecondary
     */
    void updateCategory(CategorySecondary categorySecondary);

    List<CategorySecondary> getAllCategorySecondary();

    /**
     * 获取所有分类名
     *
     * @return 包含二级分类的Category集合
     */
    public List<Category> getCategoryNameList();

    /**
     * 获取所有分类对应的属性集
     *
     * @param id category id
     * @return 包含分类属性集合的Category
     */
    public Category getCategoryProperties(Integer id);

    /**
     * 查询分类下的属性集
     *
     * @param categoryId 分类id
     * @return
     */
    public List<String> getAllProperty(Integer categoryId);

    /**
     * 更新属性集
     *
     * @param properties 属性集合
     * @param categoryId 分类id
     */
    public void updateProperty(List<String> properties, Integer categoryId);
}

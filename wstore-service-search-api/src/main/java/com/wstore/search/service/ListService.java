package com.wstore.search.service;

import com.wstore.pojo.admin.Brand;
import com.wstore.pojo.admin.Category;

import java.util.List;

public interface ListService {

    /**
     * 获取品牌列表
     * @return
     */
    List<Brand> getBrands();

    /**
     * 获取分类列表
     * @return
     */
    List<Category> getCategories();
}

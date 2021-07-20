package com.wstore.service;

import org.springframework.stereotype.Service;
import com.wstore.mapper.CategoryMapper;
import com.wstore.mapper.PosterMapper;
import com.wstore.mapper.ProductMapper;
import com.wstore.pojo.admin.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PortalServiceImpl
 * @Author Koi
 * @Date 2018/8/14 12:26
 * @Version 1.0
 */
@Service
public class PortalService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    PosterMapper posterMapper;
    /**
     * 获取分类导航
     * 包含商品
     *
     * @return
     */

    public List<Category> getAllCategory() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("c.sort DESC");
        List<Category> categories = categoryMapper.selectNameWithSecondary(example);
        for (Category category : categories){
            List<CategorySecondary> secondary = category.getCategorySecondary();
            for (int i=0;i<secondary.size();i++){
                if (i < 2) {
                    secondary.get(i).setProducts(productMapper.selectByCategory(secondary.get(i).getId()));
                }
            }
        }
        return categories;
    }

    /**
     * 获取所有广告
     *
     * @return
     */

    public Map<String, Object> getAllPoster() {
        Map<String, Object> map = new HashMap<>();
        for (int i=1;i<6;i++){
            PosterExample example = new PosterExample();
            example.setOrderByClause("sort DESC");
            example.createCriteria().andPositionEqualTo(i).andStatusNotEqualTo(0);
            List<Poster> posters = posterMapper.selectByExample(example);
            map.put("posters"+i, posters);
        }
        return map;
    }
}

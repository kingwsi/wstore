package com.wstore.portal.service;

import com.wstore.pojo.admin.Category;
import com.wstore.pojo.admin.Poster;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName PortalService
 * @Author Koi
 * @Date 2018/8/12 13:17
 * @Version 1.0
 */
public interface PortalService {

    /**
     * 获取分类导航
     * @return
     */
    List<Category> getAllCategory();

    /**
     * 获取所有广告
     * @return
     */
    Map<String, Object> getAllPoster();
}

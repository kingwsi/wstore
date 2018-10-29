package com.wstore.admin.service;

import com.wstore.pojo.admin.Poster;

import java.util.List;
import java.util.Map;

public interface PosterService {

    /**
     * 新添加广告
     *
     * @param poster
     */
    public void addPoster(Poster poster);

    /**
     * 广告状态改变
     *
     * @param id     广告id
     * @param status 0 删除 1 下架 2上架
     */
    public void updataStatus(Integer id, Integer status);

    /**
     * 根据广告位置获取广告
     * 1-顶部广告
     * 2-轮播海报
     * 3-首页热卖
     * 4-热品推荐
     * 5-楼层广告
     *
     * @return list
     */
    public Map<String, Object> getPosterByPosition(Integer position, Integer pn);

}

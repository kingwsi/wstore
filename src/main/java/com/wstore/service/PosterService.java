package com.wstore.service;

import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wstore.mapper.PosterMapper;
import com.wstore.pojo.admin.Poster;
import com.wstore.pojo.admin.PosterExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PosterServiceImpl
 * @Author Koi
 * @Date 2018/8/11 13:20
 * @Version 1.0
 */
@Service
public class PosterService {

    @Value("${page.size}")
    private Integer PAGE_SIZE;

    @Value("${page.navigate}")
    private Integer PAGE_NAVIGATE;

    @Autowired
    PosterMapper posterMapper;

    /**
     * 新添加广告
     *
     * @param poster
     */

    public void addPoster(Poster poster) {
        poster.setCreateTime(new Date());
        if (poster.getSort()==null){
            poster.setSort(1);
        }
        poster.setStatus(1);
        posterMapper.insertSelective(poster);
    }

    /**
     * 广告状态改变
     *
     * @param id     广告id
     * @param status 0 删除 1 下架 2 上架
     */

    public void updataStatus(Integer id, Integer status) {
        Poster poster = new Poster();
        poster.setId(id);
        poster.setStatus(status);
        posterMapper.updateByPrimaryKeySelective(poster);
    }

    /**
     * 根据广告位置获取广告
     * 1-顶部广告
     * 2-轮播海报
     * 3-首页热卖
     * 4-热品推荐
     * 5-楼层广告
     *
     * @param position
     * @return
     */

    public Map<String, Object> getPosterByPosition(Integer position, Integer pn) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pn, PAGE_SIZE);

        PosterExample example = new PosterExample();
        example.createCriteria().andPositionEqualTo(position).andStatusNotEqualTo(0);
        List<Poster> posters = posterMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(posters, PAGE_NAVIGATE);
        map.put("pageInfo",pageInfo);
        return map;
    }
}

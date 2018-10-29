package com.wstore.list.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wstore.common.utils.WstoreJsonUtil;
import com.wstore.pojo.admin.Brand;
import com.wstore.pojo.admin.Category;
import com.wstore.pojo.admin.Product;
import com.wstore.pojo.search.Page;
import com.wstore.search.service.ListService;
import com.wstore.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ListController
 * @Author Koi
 * @Date 2018/8/16 14:19
 * @Version 1.0
 */
@Controller
public class ListController {

    @Reference
    SearchService searchService;

    @Reference
    ListService listService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/list")
    public Object list(Map<String, Object> map) {
        List<Brand> brands = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        //从redis中取出品牌
        String brandsJSON = stringRedisTemplate.opsForValue().get("LIST_BRANDS");
        if (brandsJSON != null) {
            brands = WstoreJsonUtil.jsonToList(brandsJSON);
        } else {
            //刷新redis中数据
            brands = listService.getBrands();
            stringRedisTemplate.opsForValue().set("LIST_BRANDS", WstoreJsonUtil.toJson(brands));
        }
        //从redis中取出分类
        String categoryJSON = stringRedisTemplate.opsForValue().get("LIST_CATEGORIES");
        if (categoryJSON != null) {
            categories = WstoreJsonUtil.jsonToList(categoryJSON);
        } else {
            //刷新redis中数据
            categories = listService.getCategories();
            stringRedisTemplate.opsForValue().set("LIST_CATEGORIES", WstoreJsonUtil.toJson(categories));
        }
        map.put("brands", brands);
        map.put("categories", categories);
        return map;
    }

    @GetMapping("/sea")
    @ResponseBody
    public Page<Product> searc(@RequestParam String keyword,
                               Integer category,
                               Integer brand) {
        Page<Product> page = searchService.searchProduct(keyword, category, brand, null);
        return page;
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword,
                         @RequestParam(defaultValue = "0") Integer category,
                         @RequestParam(defaultValue = "0") Integer brand,
                         @PageableDefault(size = 20, sort = "createTime", direction = Sort.Direction.DESC) Pageable pageable,
                         Map<String, Object> map) {
        List<Brand> brands = null;
        List<Category> categories = null;
        //从redis中取出品牌
        String brandsJSON = stringRedisTemplate.opsForValue().get("LIST_BRANDS");
        if (brandsJSON != null) {
            brands = WstoreJsonUtil.jsonToList(brandsJSON);
        } else {
            //刷新redis中数据
            brands = listService.getBrands();
            stringRedisTemplate.opsForValue().set("LIST_BRANDS", WstoreJsonUtil.toJson(brands));
        }
        //从redis中取出分类
        String categoryJSON = stringRedisTemplate.opsForValue().get("LIST_CATEGORIES");
        if (categoryJSON != null) {
            categories = WstoreJsonUtil.jsonToList(categoryJSON);
        } else {
            //刷新redis中数据
            categories = listService.getCategories();
            stringRedisTemplate.opsForValue().set("LIST_CATEGORIES", WstoreJsonUtil.toJson(categories), -1);
        }

        map.put("brands", brands);
        map.put("categories", categories);

        //搜索
        if (category == 0) {
            category = null;
        }
        if (brand == 0) {
            brand = null;
        }

        Page<Product> page = searchService.searchProduct(keyword, category, brand, pageable);
        map.put("page", page);
        return "search";
    }

    /**
     * 测试
     * @param keyword
     * @param pageable
     * @return
     */
    @GetMapping("/search/json")
    @ResponseBody
    public Object test(String keyword, @PageableDefault(size = 20, page = 1) Pageable pageable) {
        if (keyword == null) {
            keyword = "";
        }
        searchService.batchOnSale();
        Page<Product> page = searchService.searchProduct(keyword, null, null, pageable);
        return null;
    }
}

package com.wstore.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wstore.admin.service.CategoryService;
import com.wstore.common.utils.WstoreJsonUtil;
import com.wstore.common.utils.WstoreResultMsg;
import com.wstore.pojo.admin.Category;
import com.wstore.pojo.admin.CategorySecondary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CategoryController
 * @Author Koi
 * @Date 2018/8/8 9:54
 * @Version 1.0
 */
@Controller
public class CategoryController {

    @Reference
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 获取所有分类  一级/二级
     *
     * @param map
     * @return
     */
    @GetMapping("/category")
    public String getCategory(Map<String, Object> map) {
        List<Category> categories = categoryService.getAllCategory();
        List<CategorySecondary> categorySecondaries = categoryService.getAllCategorySecondary();
        map.put("categories", categories);
        map.put("categorySecondaries", categorySecondaries);
        return "category";
    }

    /**
     * 添加一级分类
     *
     * @param category
     * @return
     */
    @PostMapping("/category")
    @ResponseBody
    public WstoreResultMsg addCategory(Category category) {
        if (category == null) {
            return WstoreResultMsg.fail();
        }

        if (category.getName().length() < 2 || category.getName().length() > 8) {
            return WstoreResultMsg.fail().add("error", "请输入2-8个字");
        }

        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        category.setStatus(0);

        categoryService.addCategory(category);
        return WstoreResultMsg.success();
    }

    /**
     * 修改一级分类
     *
     * @param category
     * @return
     */
    @PutMapping("/category")
    @ResponseBody
    public WstoreResultMsg updateCategory(@RequestBody Category category) {
        if (category.getName().length() < 2 || category.getName().length() > 10) {
            return WstoreResultMsg.fail().add("error", "请输入2-10个字");
        }
        if (category.getSort() == null) {
            category.setSort(1);
        }
        category.setUpdateTime(new Date());

        categoryService.updateCategory(category);
        //存入redis
        List<Category> allCategory = categoryService.getAllCategory();
        stringRedisTemplate.opsForValue().set("LIST_CATEGORIES",WstoreJsonUtil.toJson(allCategory));
        return WstoreResultMsg.success();
    }

    /**
     * 修改二级分类
     *
     * @param categorySecondary
     * @return
     */
    @PutMapping("/categorySecondary")
    @ResponseBody
    public WstoreResultMsg updateCategory(@RequestBody CategorySecondary categorySecondary) {
        if (categorySecondary == null) {
            return WstoreResultMsg.fail().add("error", "空值");
        }

        if (categorySecondary.getName().length() < 2 || categorySecondary.getName().length() > 8) {
            return WstoreResultMsg.fail().add("error", "请输入2-8个字");
        }

        if (categorySecondary.getSort() == null) {
            categorySecondary.setSort(1);
        }
        categorySecondary.setStatus(0);
        categorySecondary.setUpdateTime(new Date());

        categoryService.updateCategory(categorySecondary);
        return WstoreResultMsg.success();
    }

    /**
     * 添加二级分类
     *
     * @param categorySecondary
     * @return
     */
    @PostMapping("/categorySecondary")
    @ResponseBody
    public WstoreResultMsg addCategorySecondary(CategorySecondary categorySecondary) {
        if (categorySecondary == null) {
            return WstoreResultMsg.fail().add("error", "空值");
        }

        if (categorySecondary.getName().length() < 2 || categorySecondary.getName().length() > 8) {
            return WstoreResultMsg.fail().add("error", "请输入2-8个字");
        }

        if (categorySecondary.getSort() == null) {
            categorySecondary.setSort(1);
        }
        categorySecondary.setCreateTime(new Date());
        categorySecondary.setUpdateTime(new Date());
        categorySecondary.setStatus(0);
        categoryService.addCategory(categorySecondary);
        return WstoreResultMsg.success();
    }

    /**
     * 编辑属性集查询
     *
     * @param id
     * @return
     */
    @GetMapping("/category/properties/{id}")
    @ResponseBody
    public WstoreResultMsg getProperties(@PathVariable("id") Integer id) {
        List<String> allProperty = categoryService.getAllProperty(id);
        return WstoreResultMsg.success().add("properties", allProperty);
    }

    /**
     * 编辑属性集 提交
     * @param properties
     * @param categoryId
     * @return
     */
    @PostMapping("/category/property")
    @ResponseBody
    public WstoreResultMsg uodateProperties(String properties,Integer categoryId){
        //解析属性集合
        String[] split = properties.split("，");
        categoryService.updateProperty(Arrays.asList(split), categoryId);
        return WstoreResultMsg.success();
    };
}

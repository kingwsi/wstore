package com.wstore.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wstore.admin.service.BrandService;
import com.wstore.common.utils.*;
import com.wstore.pojo.admin.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 品牌管理
 *
 * @ClassName BrandController
 * @Author Koi
 * @Date 2018/8/1 13:26
 * @Version 1.0
 */
@Controller
public class BrandController {

    @Reference
    BrandService brandService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/brand/page/{pn}")
    public Object getBrands(@PathVariable("pn") Integer pn, Map<String, Object> map) {
        Map<String, Object> brands = brandService.getBrands(pn);
        map.put("brands", brands);
        return "brand";
    }

    /**
     * 修改品牌状态 上/下架
     * @param id     品牌id
     * @param status 状态 0-下架 1-上架
     * @return
     */
    @PostMapping("/brand/status/{id}")
    @ResponseBody
    public WstoreResultMsg updateStatus(@PathVariable("id") Integer id, Integer status) {
        Brand brand = new Brand();
        brand.setId(id);
        if (status != 0 && status != 1) {
            return WstoreResultMsg.fail().add("error", "参数错误，请重试！");
        }
        brand.setStatus(status);
        //更新品牌状态并返回list
        List<Brand> brands = brandService.updataBrand(brand);
        stringRedisTemplate.opsForValue().set("LIST_BRANDS",WstoreJsonUtil.toJson(brands));
        return WstoreResultMsg.success();
    }

    /**
     * 新增brand
     * @param brand
     * @return
     */
    @PostMapping("/brand")
    @ResponseBody
    public WstoreResultMsg addBrand(Brand brand){
        if(brand.getName().length()<2 || brand.getName().length()>8){
            return WstoreResultMsg.fail().add("error", "请输入2-8位中文品牌名！");
        }
        if(brand.getEnglishName().length()<2 || brand.getEnglishName().length()>16){
            return WstoreResultMsg.fail().add("error", "请输入2-16位英文名！");
        }
        if (brand.getBrandPic()==null || brand.getBrandPic().equals("")){
            return WstoreResultMsg.fail().add("error", "添加品牌Logo！");
        }
        if (!brandService.repeatBrand(brand)){
            return WstoreResultMsg.fail().add("error", "品牌重复！");
        }

        if (brand.getSort()==null){
            brand.setSort(1);
        }
        brand.setFirstLetter(WstoreStringUtils.getFirstLetter(brand.getName().substring(0,1)));
        brand.setStatus(0);
        brand.setCreateTime(new Date());
        brand.setUpdateTime(new Date());

        brandService.addBrand(brand);
        return WstoreResultMsg.success();
    }

    /**
     * 删除brand
     * @param
     * @return
     */
    @DeleteMapping("/brand/{id}")
    @ResponseBody
    public WstoreResultMsg deletebrand(@PathVariable("id") Integer id){
        Brand brand = new Brand();
        brand.setId(id);
        brandService.deleteBrand(brand);
        return WstoreResultMsg.success();
    }

    @PostMapping("/brand/update")
    @ResponseBody
    public WstoreResultMsg updateBrands(Brand brand){
        if (brand.getBrandPic().length()<1){
            brand.setBrandPic(null);
        }
        brand.setStatus(0);
        //更新品牌状态并返回list
        List<Brand> brands = brandService.updataBrand(brand);
        stringRedisTemplate.opsForValue().set("LIST_BRANDS",WstoreJsonUtil.toJson(brands));
        return WstoreResultMsg.success();
    }
}

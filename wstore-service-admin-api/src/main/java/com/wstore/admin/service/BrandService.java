package com.wstore.admin.service;

import com.wstore.pojo.admin.Brand;
import com.wstore.pojo.admin.Product;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BrandService
 * @Author Koi
 * @Date 2018/8/1 14:56
 * @Version 1.0
 */
public interface BrandService {

    /**
     * 获取所有品牌列表 完整信息
     * @return
     */
    public Map<String, Object> getBrands(Integer pageNum);


    /**
     * 添加品牌
     * @param brand
     */
    public void addBrand(Brand brand);

    /**
     * 更新品牌信息
     * @param brand
     */
    public List<Brand> updataBrand(Brand brand);

    /**
     * 删除品牌
     * @param brand
     */
    public void deleteBrand(Brand brand);

    /**
     * 重复查询
     * @param brand
     * @return
     */
    public boolean repeatBrand(Brand brand);

    /**
     * 查询所有品牌，部分字段查询
     * @return
     */
    public List<Brand> getBrandsPart();

}

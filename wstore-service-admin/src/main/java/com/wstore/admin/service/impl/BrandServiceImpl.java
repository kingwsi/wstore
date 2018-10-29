package com.wstore.admin.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wstore.admin.service.BrandService;
import com.wstore.mapper.BrandMapper;
import com.wstore.pojo.admin.Brand;
import com.wstore.pojo.admin.BrandExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BrandServiceImpl
 * @Author Koi
 * @Date 2018/8/1 15:01
 * @Version 1.0
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Value("${page.size}")
    private Integer PAGE_SIZE;

    @Value("${page.navigate}")
    private Integer PAGE_NAVIGATE;

    @Autowired
    BrandMapper brandMapper;

    /**
     * 获取所有品牌列表
     *
     * @param pageNum
     * @return
     */
    @Override
    public Map<String, Object> getBrands(Integer pageNum) {
        Map<String, Object> map = new HashMap<>();
        PageHelper.startPage(pageNum, PAGE_SIZE);
        List<Brand> brands = brandMapper.selectByExample(null);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands, PAGE_NAVIGATE);
        map.put("pageInfo",pageInfo);
        return map;
    }

    /**
     * 添加品牌
     *
     * @param brand
     */
    @Override
    public void addBrand(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 更新品牌信息
     *
     * @param brand
     */
    @Override
    public List<Brand> updataBrand(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
        return getBrandsPart();
    }

    /**
     * 删除品牌
     *
     * @param brand
     */
    @Override
    public void deleteBrand(Brand brand) {
        brandMapper.deleteByPrimaryKey(brand.getId());
    }

    /**
     * 重复查询
     *
     * @param brand
     * @return
     */
    @Override
    public boolean repeatBrand(Brand brand) {
        BrandExample example = new BrandExample();
        BrandExample.Criteria criteria = example.createCriteria().andNameEqualTo(brand.getName());
        BrandExample.Criteria criteria1 = example.createCriteria().andEnglishNameEqualTo(brand.getEnglishName());
        example.or(criteria1);
        List<Brand> brands = brandMapper.selectByExample(example);
        return brands.size() == 0 ? true : false;
    }

    /**
     * 查询所有品牌，部分字段查询
     *
     * @return
     */
    @Override
    public List<Brand> getBrandsPart() {
        BrandExample example = new BrandExample();
        example.createCriteria().andStatusEqualTo(1);
        example.setOrderByClause("first_letter");
        return brandMapper.selectPart(example);
    }
}

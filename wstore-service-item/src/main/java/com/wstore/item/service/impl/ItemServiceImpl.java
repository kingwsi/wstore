package com.wstore.item.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wstore.item.service.ItemService;
import com.wstore.mapper.ProductMapper;
import com.wstore.mapper.SkuMapper;
import com.wstore.pojo.admin.Product;
import com.wstore.pojo.admin.ProductExample;
import com.wstore.pojo.admin.SkuExample;
import com.wstore.pojo.item.ItemPage;
import com.wstore.pojo.item.SkuAllProp;
import com.wstore.pojo.item.SkuPolymerization;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName ItemServiceImpl
 * @Author Koi
 * @Date 2018/8/15 15:07
 * @Version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    SkuMapper skuMapper;

    @Override
    public Product getProduct(Long id){
        Product product = productMapper.WithAllByPrimaryKey(id);
        return product;
    }
    /**
     * 获取sku信息
     *
     * @param code
     * @return
     */
    @Override
    public ItemPage getProductByCode(String code) {
        ItemPage itemPage = new ItemPage();
        List<SkuAllProp> skuAllProps = skuMapper.selectAllProperties(code);
        List<SkuPolymerization> skuPolymerizations = skuMapper.selectSkuPolymerizationByCode(code);
        itemPage.setSkuAllProps(skuAllProps);
        itemPage.setSkuPolymerizations(skuPolymerizations);
        return itemPage;
    }
}

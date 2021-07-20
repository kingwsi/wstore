package com.wstore.service;

import org.springframework.stereotype.Service;
import com.wstore.mapper.ProductMapper;
import com.wstore.mapper.SkuMapper;
import com.wstore.pojo.admin.Product;
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
public class ItemService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    SkuMapper skuMapper;

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
    public ItemPage getProductByCode(String code) {
        ItemPage itemPage = new ItemPage();
        List<SkuAllProp> skuAllProps = skuMapper.selectAllProperties(code);
        List<SkuPolymerization> skuPolymerizations = skuMapper.selectSkuPolymerizationByCode(code);
        itemPage.setSkuAllProps(skuAllProps);
        itemPage.setSkuPolymerizations(skuPolymerizations);
        return itemPage;
    }
}

package com.wstore.service;

import com.wstore.mapper.SkuMapper;
import com.wstore.pojo.admin.Sku;
import com.wstore.pojo.admin.SkuExample;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName StoreServiceImpl
 * @Author wangshu
 * @Date 18-10-29 下午10:39
 * @Version 1.0
 */
public class StoreService {

    @Autowired
    SkuMapper skuMapper;

    /**
     * 对库存数量进行操作
     *
     * @param sku
     */

    public void updataStore(Sku sku) {
        SkuExample skuExample = new SkuExample();
        skuExample.createCriteria().andSkuCodeEqualTo(sku.getSkuCode());
        skuMapper.updateByExampleSelective(sku,skuExample);
    }
}

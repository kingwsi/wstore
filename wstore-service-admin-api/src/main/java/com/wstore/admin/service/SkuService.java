package com.wstore.admin.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wstore.common.utils.WstoreResultMsg;
import com.wstore.pojo.admin.Sku;
import com.wstore.pojo.admin.SkuProperty;

import java.util.List;
import java.util.Map;

public interface SkuService {

    /**
     * 校验sku是否已存在
     *
     * @param skuProperty sku对象
     * @return true-存在  false-不存在
     */
    public boolean skuIsExist(SkuProperty skuProperty);

    /**
     * 新增加sku
     *
     * @param sku
     */
    public void addSku(Sku sku);

    /**
     * 获取所有sku
     *
     * @param productCode 商品编码
     * @return 商品sku集合
     */
    public List<Sku> getAllSku(String productCode);

    /**
     * 根据主键返回一个sku
     * @param id
     * @return
     */
    public Sku selectSkuBy(Integer id);

    /**
     * 更新sku
     * @param sku
     */
    public void updateSku(Sku sku);

    /**
     * 删除sku
     * @param skuCode
     */
    void deleteSkuByCode(String skuCode);

    /**
     * 刷新最高/低价格
     * @param productCode
     */
    void flushMaxMinPrice(String productCode);
}

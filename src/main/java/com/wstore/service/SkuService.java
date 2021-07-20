package com.wstore.service;

import org.springframework.stereotype.Service;
import com.wstore.common.utils.WStoreDateUtils;
import com.wstore.mapper.ProductMapper;
import com.wstore.mapper.SkuMapper;
import com.wstore.mapper.SkuPropertyMapper;
import com.wstore.pojo.admin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName SkuServiceImpl
 * @Author Koi
 * @Date 2018/8/8 20:36
 * @Version 1.0
 */
@Service
public class SkuService {

    protected static Logger logger=LoggerFactory.getLogger(SkuService.class);

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    SkuPropertyMapper skuPropertyMapper;

    @Autowired
    ProductMapper productMapper;

    @Value("${page.size}")
    private Integer PAGE_SIZE;

    @Value("${page.navigate}")
    private Integer PAGE_NAVIGATE;

    /**
     * 校验sku是否已存在
     *
     * @param skuProperty sku属性集对象
     * @return true-存在  false-不存在
     */

    public boolean skuIsExist(SkuProperty skuProperty) {
        SkuPropertyExample example = new SkuPropertyExample();
        example.createCriteria().andProductCodeEqualTo(skuProperty.getProductCode())
                .andPropertyNameEqualTo(skuProperty.getPropertyName())
                .andPropertyNameEqualTo(skuProperty.getPropertyName());
        List<SkuProperty> skuProperties = skuPropertyMapper.selectByExample(example);
        return skuProperties.size() > 0 ? true : false;
    }

    /**
     * 新增加sku
     *
     * @param sku
     */
    @Transactional

    public void addSku(Sku sku) {
        //生成sku编码
        String code = WStoreDateUtils.generateCode();
        sku.setSkuCode(code);
        sku.setCreateTime(new Date());
        sku.setUpdateTime(new Date());

        logger.debug("新增SKU");
        //sku属性集插入
        List<SkuProperty> skuProperties = sku.getSkuProperties();
        for (SkuProperty skuProperty : skuProperties){
            //设置sku编码
            skuProperty.setSkuCode(code);
            //清空id
            skuProperty.setId(null);
            //设置商品编码
            skuProperty.setProductCode(sku.getProductCode());
            skuPropertyMapper.insertSelective(skuProperty);
        }
        skuMapper.insertSelective(sku);
        //更新价格
        flushMaxMinPrice(sku.getProductCode());
    }

    /**
     * 获取所有sku
     *
     * @param productCode 商品编码
     * @return 商品sku集合
     */

    public List<Sku> getAllSku(String productCode) {
        List<Sku> skus = skuMapper.selectWithPropertyByCode(productCode);
        return skus;
    }

    /**
     * 根据主键返回一个sku
     *
     * @param id
     * @return
     */

    public Sku selectSkuBy(Integer id) {
        return skuMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新sku
     *
     * @param sku
     */
    @Transactional

    public void updateSku(Sku sku) {
        List<SkuProperty> skuProperties = sku.getSkuProperties();
        for (SkuProperty skuProperty : skuProperties){
            skuPropertyMapper.updateByPrimaryKeySelective(skuProperty);
        }
        skuMapper.updateByPrimaryKeySelective(sku);
        flushMaxMinPrice(sku.getProductCode());
    }
    /**
     * 删除sku
     *
     * @param code
     */

    public void deleteSkuByCode(String code) {

        SkuExample example = new SkuExample();
        example.createCriteria().andSkuCodeEqualTo(code);
        skuMapper.deleteByExample(example);
        logger.info("删除商品：ID---"+code);
    }

    /**
     * 刷新最高/低价格
     *
     * @param productCode
     */

    public void flushMaxMinPrice(String productCode) {
        Product product = skuMapper.findMaxAndMinPrice(productCode);
        if (product == null){
            product = new Product();
            product.setMinPrice(0);
            product.setMaxPrice(0);
        }
        //下架商品
        product.setStatus(0);
        ProductExample example = new ProductExample();
        example.createCriteria().andCodeEqualTo(productCode);
        productMapper.updateByExampleSelective(product, example);
        logger.info("最高/低价格已更新，商品ID---"+productCode);
    }
}

package com.wstore.admin.service;

import com.wstore.pojo.admin.Product;
import com.wstore.pojo.admin.ProductExtend;
import com.wstore.pojo.admin.ProductImage;
import com.wstore.pojo.admin.ProductProperty;

import java.util.List;
import java.util.Map;

/**
 * 商品管理接口
 *
 * @author koi
 */
public interface ProductService {

    /**
     * 获取所有商品
     *
     * @return
     */
    public Map<String, Object> getProducts(Integer pageNum);

    /**
     * 新增商品
     *
     * @param product
     */

    public void addProduct(Product product);

    /**
     * 修改产品信息
     *
     * @param product
     */
    public void updateProduct(Product product);

    /**
     * 根据商品id上架商品
     *
     * @param product
     */
    public boolean onSaleProduct(Product product);

    /**
     * 根据商品id下架商品
     *
     * @param product
     */
    public void offSaleProduct(Product product);

    /**
     * 商品拆分信息，扩展图文详情
     *
     * @param productExtend
     */
    public void addProductExtend(ProductExtend productExtend);

    /**
     * 插入商品图片集
     *
     * @param productImage
     */
    public void addProductImages(ProductImage productImage);

    /**
     * 插入商品属性集
     *
     * @param productProperty
     */
    public void addProductProperties(ProductProperty productProperty);

    /**
     * 编辑商品
     * 联合商品扩展表
     * 按主键查询所有相关信息
     *
     * @param pid
     * @return
     */
    public Product getProductJoint(Long pid);

    /**
     * 根据商品编码查询此商品的属性集
     *
     * @param
     * @return
     */
    public Product getProperties(String code);

    /**
     * 删除商品
     *
     * @param code
     */
    List<String> deleteProductByCode(String code);

    /**
     * 批量下架
     * @return
     */
    public Integer batchOffSale();
}

package com.wstore.pojo.item;

import com.wstore.pojo.admin.Sku;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Sku聚合类
 * 属性组合生成sku信息
 * @ClassName SkuPolymerization
 * @Author Koi
 * @Date 2018/8/20 8:29
 * @Version 1.0
 */
@Entity
@Table
public class SkuPolymerization {

    private String skuKey;

    @Id
    private Integer id;

    private Integer stock;

    private Integer price;

    private Integer frozenStock;

    public String getSkuKey() {
        return skuKey;
    }

    public void setSkuKey(String skuKey) {
        this.skuKey = skuKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getFrozenStock() {
        return frozenStock;
    }

    public void setFrozenStock(Integer frozenStock) {
        this.frozenStock = frozenStock;
    }
}

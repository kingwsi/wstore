package com.wstore.pojo.item;

import com.wstore.pojo.admin.Product;
import com.wstore.pojo.admin.Sku;

import java.util.List;

/**
 * item页面信息封装
 * @ClassName ItemPage
 * @Author Koi
 * @Date 2018/8/19 22:47
 * @Version 1.0
 */
public class ItemPage {

    //所有sku属性集合
    private List<SkuAllProp> skuAllProps;

    //属性组合结果集
    private List<SkuPolymerization> skuPolymerizations;

    public List<SkuAllProp> getSkuAllProps() {
        return skuAllProps;
    }

    public void setSkuAllProps(List<SkuAllProp> skuAllProps) {
        this.skuAllProps = skuAllProps;
    }

    public List<SkuPolymerization> getSkuPolymerizations() {
        return skuPolymerizations;
    }

    public void setSkuPolymerizations(List<SkuPolymerization> skuPolymerizations) {
        this.skuPolymerizations = skuPolymerizations;
    }
}

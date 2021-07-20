package com.wstore.pojo.item;

import java.util.List;
import java.util.Set;

/**
 * sku所有可选属性组合
 * @ClassName SkuAllProp
 * @Author Koi
 * @Date 2018/8/20 8:34
 * @Version 1.0
 */
public class SkuAllProp {

    //属性名
    private String propertyName;
    //属性名下对应的所有属性
    private List<String> value;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }
}

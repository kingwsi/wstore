package com.wstore.item.service;

import com.wstore.pojo.admin.Product;
import com.wstore.pojo.item.ItemPage;

public interface ItemService {

    Product getProduct(Long id);

    /**
     * 获取商品信息
     * @param code
     * @return
     */
    public ItemPage getProductByCode(String code);
}

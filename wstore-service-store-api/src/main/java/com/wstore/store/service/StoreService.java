package com.wstore.store.service;

import com.wstore.pojo.admin.Sku;

public interface StoreService {

    /**
     * 对库存数量进行操作
     * @param sku
     */
    void updataStore(Sku sku);
}

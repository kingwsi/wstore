package com.wstore.search.service;

import com.wstore.pojo.admin.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    long batchOnSale();

    /**
     * 添加商品信息到es
     * @param product
     */
    public void addProductToES(Product product);

    void bulkIndex(List<Product> ProductList);

    /**
     * 从elasticsearch移除product
     * @param product
     */
    public void removeProduct(Product product);

    public com.wstore.pojo.search.Page<Product> searchProduct(String name, Integer CategoryId, Integer brandId, Pageable pageable);

    List<Product> getProduct(Integer startRow, Integer endRow);
}

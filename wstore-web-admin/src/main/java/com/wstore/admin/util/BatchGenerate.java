package com.wstore.admin.util;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wstore.admin.service.ProductService;

/**
 * @ClassName BatchGenerate
 * @Author Koi
 * @Date 2018/8/17 20:38
 * @Version 1.0
 */
public class BatchGenerate {
    @Reference
    ProductService productService;

    public static void main(String[] args) {

    }

}

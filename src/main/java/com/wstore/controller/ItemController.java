package com.wstore.controller;


import com.wstore.common.utils.WstoreJsonUtil;
import com.wstore.pojo.admin.Product;
import com.wstore.pojo.item.ItemPage;
import com.wstore.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @ClassName ItemController
 * @Author Koi
 * @Date 2018/8/19 18:01
 * @Version 1.0
 */
@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private final static String ITEM_PRODUCT_KEY = "ITEM:PRODUCT_KEY_";

    private final static String ITEM_SKU_KEY = "ITEM:SKU_KEY_";

    /**
     * 跳转到商品Item页面
     * @param map
     * @param id
     * @return
     */
    @GetMapping("/item/product/{code}")
    public String toItem(Map<String, Object> map, @PathVariable("code") Long id){
        Product product = null;
        String productJson = stringRedisTemplate.opsForValue().get(ITEM_PRODUCT_KEY + id);
        //redis中不存在数据则查询数据库
        if (productJson==null || productJson.equals("null")){
            product = itemService.getProduct(id);
            //数据库查询商品
            if (product != null){
                stringRedisTemplate.opsForValue().set(ITEM_PRODUCT_KEY + id,WstoreJsonUtil.toJson(product));
                map.put("product",product);
                return "item";
            }
            //找不到商品返回页
            return "/";
        } else {
            product = WstoreJsonUtil.fromJson(productJson,Product.class);
            map.put("product",product);
            return "item";
        }
    }

    @GetMapping("/item/product/json")
    @ResponseBody
    public Product json(){
        Product product = itemService.getProduct(Long.parseLong("8"));
        return product;
    }

    /**
     * sku聚合信息
     * 组合
     * @param code
     * @return JSON串
     */
    @GetMapping("/item/{code}")
    @ResponseBody
    public String item(@PathVariable("code") String code){
        String polymerizationJson = stringRedisTemplate.opsForValue().get(ITEM_SKU_KEY + code);
        if (polymerizationJson==null){
            ItemPage skuPolymerization = itemService.getProductByCode(code);
            stringRedisTemplate.opsForValue().set(ITEM_SKU_KEY + code, WstoreJsonUtil.toJson(skuPolymerization));
            return WstoreJsonUtil.toJson(skuPolymerization);
        }
        return polymerizationJson;
    }
}

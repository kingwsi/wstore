package com.wstore.admin.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wstore.admin.service.ProductService;
import com.wstore.admin.service.SkuService;
import com.wstore.common.utils.WstoreResultMsg;
import com.wstore.pojo.admin.Product;
import com.wstore.pojo.admin.Sku;
import com.wstore.pojo.admin.SkuProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SkuController
 * @Author Koi
 * @Date 2018/8/8 16:18
 * @Version 1.0
 */
@Controller
public class SkuController {

    protected static Logger logger=LoggerFactory.getLogger(SkuController.class);

    @Reference
    ProductService productService;

    @Reference
    SkuService skuService;

    /**
     * 跳转到添加商品sku页面
     * 查询相关数据 product的属性集
     * @return
     */
    @GetMapping("/product/sku/{code}")
    public String getProperties(@PathVariable("code") String code,Map<String,Object> map){
        Product product = productService.getProperties(code);
        List<Sku> skus = skuService.getAllSku(code);
        map.put("product",product);
        map.put("skuList",skus);
        return "sku-product";
    }

    /**
     * 新增SKU信息
     * @return
     * @param properties json格式的属性list
     * @param skuJson json格式的sku
     */
    @PostMapping("/product/sku")
    @ResponseBody
    public WstoreResultMsg addSku(String skuJson,String properties){
        Gson gson = new Gson();
        //反序列化SkuProperty
        Type type = new TypeToken<ArrayList<SkuProperty>>() {
        }.getType();
        ArrayList<SkuProperty> list = gson.fromJson(properties, type);

        Sku sku = null;
        //反序列化sku
        try {
            sku = gson.fromJson(skuJson, Sku.class);
        } catch (Exception e){
            logger.error(e.getMessage());
            return WstoreResultMsg.fail().add("error","SKU格式输入错误！");
        }
        sku.setSkuProperties(list);
        sku.setMarketPrice(sku.getMarketPrice() * 100);
        sku.setCostPrice(sku.getCostPrice() * 100);
        sku.setPrice(sku.getPrice() * 100);
        if (sku.getId()!=null&&sku.getId()!=0){
            //更新数据
            skuService.updateSku(sku);
            logger.info("更新SKU --SKU_ID="+sku.getId());
            return WstoreResultMsg.success();
        }
        //新增sku清空id信息
        sku.setId(null);
        skuService.addSku(sku);
        return WstoreResultMsg.success();
    }

    /**
     * 删除sku
     * @param skuCode
     * @param propCode
     * @return
     */
    @DeleteMapping("/product/sku/{skuCode}/{propCode}")
    @ResponseBody
    public WstoreResultMsg deleteSku(@PathVariable("skuCode") String skuCode, @PathVariable("propCode") String propCode){
        skuService.deleteSkuByCode(skuCode);
        skuService.flushMaxMinPrice(propCode);
        return WstoreResultMsg.success();
    }


    @GetMapping("/sku/info/json")
    @ResponseBody
    public Map<String, Object> json(){
        Map<String,Object> map = new HashMap<>();
        List<Sku> skus = skuService.getAllSku("2018080519214148");
        SkuProperty skuProperty = new SkuProperty();
        skuProperty.setPropertyName("pname");
        skuProperty.setPropertyValue("pvalue");
        skuProperty.setProductCode("2018080519214148");
        boolean isExist = skuService.skuIsExist(skuProperty);
        map.put("skuList",skus);
        return map;
    }
}

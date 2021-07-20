package com.wstore.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wstore.pojo.admin.SkuProperty;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Json工具
 * @ClassName WstoreJsonUtil
 * @Author Koi
 * @Date 2018/7/16 10:35
 * @Version 1.0
 */
public class WstoreJsonUtil {

    /**
     * 序列化一个对象返回字符串
     * @param object
     * @return
     */
    public static String toJson(Object object){
        Gson gson  = new Gson();
        return gson.toJson(object);
    }

    /**
     * json转换对象
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz){
        Gson gson = new Gson();
        return gson.fromJson(json,clazz);
    }

    /**
     * json转list对象
     * @param json 必须是数组格式的json字符串
     * @param <T> Object
     * @return
     */
    public static <T> List<T> jsonToList(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<T>>() {
        }.getType();
        ArrayList<T> list = gson.fromJson(json, type);
        return list;
    }
}

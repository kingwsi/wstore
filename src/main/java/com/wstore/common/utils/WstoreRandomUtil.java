package com.wstore.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @ClassName WstoreRandomUtil
 * @Author Koi
 * @Date 2018/7/13 10:58
 * @Version 1.0
 * 随机函数工具类
 */
public class WstoreRandomUtil {

    /**
     * 取一定长度的随机字符串
     * max - 32
     * @param size
     * @return
     */
    public static String generateRandomString(int size){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("\\-","");

        if(size==32){
            return uuid;
        }

        int beginIndex = new Random().nextInt(32-size);

        return uuid.substring(beginIndex, beginIndex + size);
    }

    /**
     * 13位订单号生成
     * @return
     */
    public static Long genOrderSn(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String sn = simpleDateFormat.format(new Date())+(System.nanoTime() + "").substring(5, 10)
                + (new Random().nextInt(90)+9);
        return Long.valueOf(sn);
    }

}

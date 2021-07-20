package com.wstore.common.utils;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

/**
 * 时间处理工具
 * @ClassName WStoreDateUtils
 * @Author Koi
 * @Date 2018/7/24 11:35
 * @Version 1.0
 */
public class WStoreDateUtils {

    /**
     * 生成时间戳 yyytMMddmmss
     * @return
     */
    public static String formatDateToInteger(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddmmss");
        return simpleDateFormat.format(System.currentTimeMillis());
    }

    /**
     * 根据时间生成 16 位编码
     * @return 16 位字符串编码
     */
    public static String generateCode(){
        return formatDateToInteger() + new Random().nextInt(9999);
    }
}

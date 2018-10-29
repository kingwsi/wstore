package com.wstore.common.utils;

import com.wstore.pojo.admin.Product;
import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * @ClassName WstoreStringUtils
 * @Author Koi
 * @Date 2018/8/1 23:43
 * @Version 1.0
 */
public class WstoreStringUtils {
    /**
     * 得到中文首字母
     *
     * @param str
     * @return
     */
    public static String getFirstLetter(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return convert;
    }

    /**
     * 去除html页面中的转义字符
     * @param html
     * @return
     */
    public static String getRealHTML(String html){
        html = html.replace("\n"," ");
        html = html.replace("\r"," ");
        html = html.replace("\t"," ");
        html = html.replace("  ","");
        return html;
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return
     */
    public static String getFileExtensionName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}

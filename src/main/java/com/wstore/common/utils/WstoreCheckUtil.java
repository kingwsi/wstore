package com.wstore.common.utils;

import java.io.File;
import java.sql.Ref;
import java.util.regex.Pattern;

/**
 * @ClassName WstoreCheckUtil
 * @Author Koi
 * @Date 2018/7/16 13:16
 * @Version 1.0
 */
public class WstoreCheckUtil {

    /**
     * 邮箱格式检查
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        return Pattern.matches(regex, email);
    }

    /**
     * 手机号码验证
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^[1][3,4,5,7,8][0-9]{9}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 长度校验 - 中文
     *
     * @param string    待校验字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return
     */
    public static boolean lengthCheckZH(String string, int minLength, int maxLength) {
        String regex = "[\\u4e00-\\u9fa5]{" + minLength + "," + maxLength + "}$";
        return Pattern.matches(regex, string);
    }

    /**
     * 长度校验 - 字母
     *
     * @param string    待校验字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return
     */
    public static boolean checkLetter(String string, int minLength, int maxLength) {
        String regex = "[A-Za-z]{" + minLength + "," + maxLength + "}$";
        return Pattern.matches(regex, string);
    }

    /**
     * 获取文件扩展名
     * @param file 文件
     * @return
     */
    public static String getFileExtensionName(File file) {
        String fileName = file.getName();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 文件扩展名校验
     * @param fileName 文件名
     * @param fileExt  文件扩展名
     * @return 返回后缀名或null
     */
    public static boolean extensionNameCheck(String fileName, String fileExt) {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (fileExt.equals(suffix))
            return true;
        return false;
    }

    /**
     * 文件扩展名校验
     * @param file    文件
     * @param fileExt 扩展名
     * @return
     */
    public static Boolean extensionNameCheck(File file, String fileExt) {
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (fileExt.equals(suffix))
            return true;
        return false;
    }
}

package com.wstore.common.utils;

import java.math.BigDecimal;

/**
 * @ClassName MathUtils
 * @Author Koi
 * @Date 2018/7/29 15:05
 * @Version 1.0
 */

public class MathUtils {
    /**
     * 加
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal add(double num1, double num2) {
        BigDecimal a1 = new BigDecimal(Double.toString(num1));
        BigDecimal a2 = new BigDecimal(Double.toString(num2));
        return a1.add(a2);
    }

    /**
     * 减
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal subtract(double num1, double num2) {
        BigDecimal a1 = new BigDecimal(Double.toString(num1));
        BigDecimal a2 = new BigDecimal(Double.toString(num2));
        return a1.subtract(a2);
    }

    /**
     * 乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal multiply(double num1, double num2) {
        BigDecimal a1 = new BigDecimal(Double.toString(num1));
        BigDecimal a2 = new BigDecimal(Double.toString(num2));
        return a1.multiply(a2);
    }

    /**
     * 除
     *
     * @param num1
     * @param num2
     * @return
     */
    public static BigDecimal divide(double num1, double num2) {
        BigDecimal a1 = new BigDecimal(Double.toString(num1));
        BigDecimal a2 = new BigDecimal(Double.toString(num2));
        return a1.divide(a2);
    }
}
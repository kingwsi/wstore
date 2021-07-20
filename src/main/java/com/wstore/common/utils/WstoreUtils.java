package com.wstore.common.utils;

/**
 * 通用工具
 *
 * @ClassName WstoreUtils
 * @Author Koi
 * @Date 2018/8/23 10:11
 * @Version 1.0
 */
public class WstoreUtils {

    /**
     * 分页 页码导航构建
     *
     * @param pages        总页数
     * @param num          当前页码
     * @param navigateSize 页码导航大小
     * @return int[] 页码导航数组
     */
    public static int[] paging(int pages, int num, int navigateSize) {

        int minNum = navigateSize / 2;
        int maxNum = minNum;
        //navigateSize为偶数九将最大页码数-1 防止数组越界
        if (navigateSize % 2 == 0) {
            maxNum -= 1;
        }
        int[] navigateNums = new int[navigateSize];
        if (pages > navigateSize) {
            if (num <= minNum) {
                //当前页小于navigateSize/2，直接构建1到n页码
                for (int i = 0; i < navigateSize; i++) {
                    navigateNums[i] = i + 1;
                }
            } else if (num > pages - navigateSize) {
                //当前页在最大页码-导航大小和最大页之间
                int flag = 0;
                for (int i = pages - navigateSize+1; i <= pages; i++) {
                    navigateNums[flag++] = i;
                }
            } else {
                int flag = 0;
                for (int i = num - minNum; i <= num + maxNum; i++) {
                    navigateNums[flag++] = i;
                }
            }
        } else {
            //设置数组长度
            navigateNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigateNums[i] = i + 1;
            }
        }

        return navigateNums;
    }
}

package com.wstore.common.test;

import com.wstore.common.utils.*;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @ClassName UtilTest
 * @Author Koi
 * @Date 2018/7/13 11:16
 * @Version 1.0
 */
public class UtilTest {

    @Test
    public void test1(){
        BigDecimal multiply = MathUtils.multiply(0.05, 0.23);
        System.out.println(multiply);
    }

    @Test
    public void test01(){
        for (int i = 0;i < 50; i++){
            System.out.println(System.currentTimeMillis());
            System.out.println(System.nanoTime());
        }
    }
    @Test
    public void pathTest() throws IOException {
        File file = new File("");
        System.out.println(file.getAbsolutePath());
    }

    @Test
    public void idTest(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        for (int i = 0;i < 100;i++){
            String sn = simpleDateFormat.format(new Date())+(System.nanoTime() + "").substring(5, 10)+ (new Random().nextInt(90)+9);
            System.out.println(sn);
        }
    }
}
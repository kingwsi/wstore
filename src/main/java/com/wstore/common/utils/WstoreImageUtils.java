package com.wstore.common.utils;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

/**
 * @ClassName WstoreImageUtils
 * @Author Koi
 * @Date 2018/8/1 9:26
 * @Version 1.0
 */
public class WstoreImageUtils {

    /**
     * 图片通用检查
     *
     * @param inputStream 文件输入流
     * @param minWidth    图片最小宽度
     * @param maxSize     图片最大限制
     * @param fileName    文件名
     * @param format      待校验文件格式，如需支持多种格式，用","隔开
     * @return success通过检查
     * @throws IOException
     */
    public static WstoreResultMsg commonCheckImg(InputStream inputStream, int minWidth, int maxSize, String fileName, String format) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(inputStream);

        if (bufferedImage == null) {
            return WstoreResultMsg.fail().add("error", "文件格式错误，请检查后重新上传！");
        }
        //文件格式校验
        String[] formats = format.split(",");
        String fileExtensionName = WstoreStringUtils.getFileExtensionName(fileName);
        for (String string : formats) {
            if (fileExtensionName.equalsIgnoreCase(format)) {
                return WstoreResultMsg.fail().add("error", "请上传" + format + "格式的文件");
            }
        }

        if (bufferedImage.getWidth() < minWidth) {
            return WstoreResultMsg.fail().add("error", "请上传分宽度不小于" + minWidth + "的图片");
        }

        if (inputStream.available() > 1024 * 1024 * maxSize) {
            return WstoreResultMsg.status(202, "文件大小超出限制！请上传不大于" + maxSize + "M的文件");
        }
        return WstoreResultMsg.success();
    }

    /**
     * 填充透明像素是图片到指定宽高
     *
     * @param bufferedImage 图片流
     * @param afterHeight   要生成的高度
     * @param afterWidth    要生成的宽度
     * @return 图片流
     */
    public static InputStream fillImageSpace(BufferedImage bufferedImage, int afterWidth, int afterHeight) throws IOException {
        if (bufferedImage == null) {
            throw new IOException("图片读取错误！");
        }
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();

        BufferedImage outImage = null;

        outImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);// 2.jpg
        Graphics2D graphics2D = outImage.createGraphics();
        outImage = graphics2D.getDeviceConfiguration().createCompatibleImage(afterWidth, afterHeight, Transparency.TRANSLUCENT);
        graphics2D.dispose();
        graphics2D = outImage.createGraphics();
        //设置图片居中显示
        graphics2D.drawImage(bufferedImage, (afterWidth - width) / 2, (afterHeight - height) / 2, null);
        //返回文件流
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
        ImageIO.write(outImage, "png", imOut);
        InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
        return inputStream;
    }

    /**
     * 图片压缩
     * 等比例压缩
     *
     * @param bufferedImage 输入流
     * @param afterWidth    压缩后宽度
     * @param afterHeight   压缩后高度
     * @return bufferedImage
     * @throws IOException
     */
    public static BufferedImage compressionImage(BufferedImage bufferedImage, int afterWidth, int afterHeight) throws IOException {
        return Thumbnails.of(bufferedImage).size(afterWidth, afterHeight).asBufferedImage();
    }
}
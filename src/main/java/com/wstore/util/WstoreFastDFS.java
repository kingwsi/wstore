package com.wstore.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName WstoreFaseDFS
 * @Author Koi
 * @Date 2018/7/31 16:58
 * @Version 1.0
 */

@Component
public class WstoreFastDFS {
    protected static Logger logger=LoggerFactory.getLogger(WstoreFastDFS.class);

    /**
     * 上传文件类
     * @param file 文件
     * @param fileEx 后缀名
     * @return 文件路径
     */
    public String upload(MultipartFile file, String fileEx) throws IOException {
        try {
            logger.info("上传文件"+file.getOriginalFilename());
            return "https://dummyimage.com/200x200";
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * inputStream方式上传文件
     * @param inputStream
     * @param fileEx
     * @return
     */
    public String upload(InputStream inputStream, String fileEx) {
        try {
            logger.info("上传文件流到");
            return "https://dummyimage.com/200x200";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileUrl 文件访问地址
     * @return
     */
    public void deleteFile(String fileUrl) {
        if (fileUrl==null){
            return;
        }
    }
}
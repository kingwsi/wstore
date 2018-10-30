package com.wstore.admin.util;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    FastFileStorageClient fastFileStorageClient;

    @Value("${img_server_host}")
    private String IMG_SERVER_HOST;

    /**
     * 上传文件类
     * @param file 文件
     * @param fileEx 后缀名
     * @return 文件路径
     */
    public String upload(MultipartFile file, String fileEx) throws IOException {
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileEx , null);
            String resultPath = storePath.getGroup() + "/" + storePath.getPath();
            logger.info("上传文件"+file.getOriginalFilename()+"到"+storePath);
            return resultPath;
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
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, inputStream.available(), fileEx , null);
            String resultPath = storePath.getGroup() + "/" + storePath.getPath();
            logger.info("上传文件流到："+IMG_SERVER_HOST+storePath);
            return resultPath;
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
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
            logger.info("从"+IMG_SERVER_HOST+storePath+"删除文件"+fileUrl);
        } catch (FdfsUnsupportStorePathException e) {
            e.printStackTrace();
        }
    }
}
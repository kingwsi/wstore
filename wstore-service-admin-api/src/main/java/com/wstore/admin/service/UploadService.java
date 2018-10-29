package com.wstore.admin.service;

import java.io.File;

/**
 * 上传文件
 * @ClassName UploadService
 * @Author Koi
 * @Date 2018/7/29 22:51
 * @Version 1.0
 */
public interface UploadService {

    /**
     * 上传文件
     * @param file
     * @return 返回文件路径
     */
    public String upload(File file);
}

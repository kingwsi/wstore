package com.wstore.admin.controller;

import com.wstore.admin.util.WstoreFastDFS;
import com.wstore.common.utils.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UploadController
 * @Author Koi
 * @Date 2018/7/29 22:58
 * @Version 1.0
 */
@Controller
public class UploadController {

    @Autowired
    WstoreFastDFS wstoreFastDFS;

    @Value("${img_server_host}")
    private String IMG_SERVER_HOST;

    @Value("${upload_picture_size}")
    private Integer IMAGE_MAX_SIZE;

    @Value("${upload_picture_min_width}")
    private Integer IMAGE_MIN_WIDTH;

    @Value("${upload_picture_format}")
    private String IMAGE_FORMAT;

    /**
     * 产品图片集上传
     * 处理图片为宽高 1:1
     * 图片边长为原始图片最大边长
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/product/picture")
    @ResponseBody
    public WstoreResultMsg uploadPicture(MultipartFile file, HttpServletRequest request) throws IOException {

        String ImgResultPath = "";

        /*图片通用检验*/
        WstoreResultMsg wstoreResultMsg = WstoreImageUtils
                .commonCheckImg(file.getInputStream(), IMAGE_MIN_WIDTH, IMAGE_MAX_SIZE, file.getOriginalFilename(), IMAGE_FORMAT);
        if (wstoreResultMsg.getCode() != 100) {
            return wstoreResultMsg;
        }

        //图片最小宽/高不小于250px
        BufferedImage read = ImageIO.read(file.getInputStream());
        int width = read.getWidth();
        int height = read.getHeight();

        int maxWidth = width > height ? width : height;

        if (maxWidth < 250){
            return WstoreResultMsg.fail().add("error","请上传分辨率250 * 250以上的商品图片！");
        }
        String fileExtensionName = WstoreStringUtils.getFileExtensionName(file.getOriginalFilename());
        if (width != height) {
            int side = width > height ? width : height;
            ImgResultPath = wstoreFastDFS.upload(WstoreImageUtils.fillImageSpace(read, side, side), "png");
        } else {
            ImgResultPath = wstoreFastDFS.upload(file, fileExtensionName);
        }
        return WstoreResultMsg.success().add("imgPath", "http://" + IMG_SERVER_HOST + "/" + ImgResultPath).add("imgName", ImgResultPath);
    }

    /**
     * 删除图片
     * @param name
     * @return
     */
    @DeleteMapping("/upload/picture")
    @ResponseBody
    public WstoreResultMsg deleteimg(@RequestBody String name) {
        if (name.length() < 1) {
            return WstoreResultMsg.fail().add("error", "系统错误，请刷新重试！");
        }
        wstoreFastDFS.deleteFile("/" + name);
        return WstoreResultMsg.success();
    }

    /**
     * logo上传
     * 图片处理位宽高比3:1的图片
     *
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/brand/logo")
    @ResponseBody
    public WstoreResultMsg uploadLogo(MultipartFile file, HttpServletRequest request) throws IOException {

        /*图片通用检验*/
        WstoreResultMsg wstoreResultMsg = WstoreImageUtils
                .commonCheckImg(file.getInputStream(), IMAGE_MIN_WIDTH, IMAGE_MAX_SIZE, file.getOriginalFilename(), IMAGE_FORMAT);
        if (wstoreResultMsg.getCode() != 100) {
            return wstoreResultMsg;
        }
        InputStream is = file.getInputStream();
        BufferedImage read = ImageIO.read(is);

        /*图片高度超过200的按200处理*/
        int height = read.getHeight();
        if (height > 200){
            height = 200;
            read = WstoreImageUtils.compressionImage(read,height * 3, height);
        }
        int width = height * 3;
        InputStream inputStream = WstoreImageUtils.fillImageSpace(read, width, height);

        String fileExtensionName = WstoreStringUtils.getFileExtensionName(file.getOriginalFilename());
        String ImgResultPath = wstoreFastDFS.upload(inputStream, fileExtensionName);

        return WstoreResultMsg.success().add("imgPath", "http://" + IMG_SERVER_HOST + "/" + ImgResultPath).add("imgName", ImgResultPath);
    }

    /**
     * 产品扩展信息，图片上传
     * @param file
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/product/extend/image")
    @ResponseBody
    public Map<String, Object> uploadProductExtend(MultipartFile file, HttpServletRequest request) throws IOException {
        //封装wangEdit返回结果集
        Map<String,Object> map = new HashMap<>();
        map.put("errno",0);
        /*图片通用检验*/
        WstoreResultMsg wstoreResultMsg = WstoreImageUtils
                .commonCheckImg(file.getInputStream(), IMAGE_MIN_WIDTH, IMAGE_MAX_SIZE, file.getOriginalFilename(), IMAGE_FORMAT);
        if (wstoreResultMsg.getCode() != 100) {
            map.put("errno",200);
            return map;
        }

        String fileExtensionName = WstoreStringUtils.getFileExtensionName(file.getOriginalFilename());
        String imgResultPath = wstoreFastDFS.upload(file.getInputStream(), fileExtensionName);
        imgResultPath =  "http://" + IMG_SERVER_HOST + "/" + imgResultPath;

        //图片地址封装为数组
        List<String> resultImgList = new ArrayList<>();
        resultImgList.add(imgResultPath);
        map.put("data",resultImgList);
        return map;
    }

    @PostMapping("/poster/picture/{position}")
    @ResponseBody
    public WstoreResultMsg uploadPoster(MultipartFile file, @PathVariable("position") Integer position) throws IOException {
        int width, height = 0;
        switch (position){
            case 1:
                //top
                width = 3840;
                height = 240;
                break;
            case 2:
                //轮播
                width = 1920;
                height = 480;
                break;
            case 3:
                //热卖
                width = 488;
                height = 280;
                break;
            case 4:
                //推荐
                width = 480;
                height = 680;
                break;
            case 5:
                //楼层
                width = 1240;
                height = 120;
                break;
            default:
                return WstoreResultMsg.fail();
        }
        BufferedImage read = ImageIO.read(file.getInputStream());
        if(read.getHeight() != height || read.getWidth() != width){
            return WstoreResultMsg.fail().add("error","请上传分辨率为" + width +" * "+height + "的图片！");
        }
        String fileExtensionName = WstoreStringUtils.getFileExtensionName(file.getOriginalFilename());
        String ImgResultPath = wstoreFastDFS.upload(file.getInputStream(), fileExtensionName);

        return WstoreResultMsg.success().add("imgPath", "http://" + IMG_SERVER_HOST + "/" + ImgResultPath).add("imgName", ImgResultPath);
    }
}

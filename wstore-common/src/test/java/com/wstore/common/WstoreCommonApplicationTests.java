package com.wstore.common;

import com.github.tobato.fastdfs.domain.GroupState;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.TrackerClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WstoreCommonApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private TrackerClient trackerClient;

    @Test
    public void registerEmail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        String code = "JHGUYWSK";
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setSubject("【注册验证】欢迎注册Wstore");
        mimeMessageHelper.setText("<h3 style='color:#32a5e7;text-align: center;'>欢迎注册Wstore!</h3>" +
                "</br><h4>请输入验证码完成注册，您的验证码为<b>" + code + "</b>请在10分钟内完成验证</h4>", true);
        mimeMessageHelper.setTo("772399623@qq.com");
        mimeMessageHelper.setFrom("17639206656@163.com");
        mailSender.send(mimeMessage);
    }

    @Test
    public void contextLoads() {
        List<GroupState> groupStates = trackerClient.listGroups();
        for (GroupState groupState : groupStates) {
            System.out.println(groupState);
        }

    }

    /**
     * 测试文件上传
     */
    @Test
    public void upload() {

        try {
            File file = new File("D:/a598edd2.png");

            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), "png", null);

            fastFileStorageClient.uploadSlaveFile(storePath.getGroup(),storePath.getPath(),inputStream,inputStream.available(),"a_",null);
            //fastFileStorageClient.uploadSlaveFile("group1","M00/00/00/wKiAjVlpNjiAK5IHAADGA0F72jo578.jpg",inputStream,inputStream.available(),"a_",null);

            System.out.println(storePath.getGroup() + " " + storePath.getPath());

            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试上传缩略图
     */
    @Test
    public void uploadCrtThumbImage() {
        try {
            File file = new File("d:\\ds.jpg");

            FileInputStream inputStream = new FileInputStream(file);
            // 测试上传 缩略图
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream, file.length(), "jpg", null);

            System.out.println(storePath.getGroup() + "  " + storePath.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试文件下载
     */
    @Test
    public void download() {
        try {
            byte[] bytes = fastFileStorageClient.downloadFile("group1", "M00/00/00/wKiAjVlpMfiAagnbAADGA0F72jo134_150x150.jpg", new DownloadByteArray());

            FileOutputStream stream = new FileOutputStream("a.jpg");

            stream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试文件删除
     */
    @Test
    public void deleteFile(){
        fastFileStorageClient.deleteFile("group1","M00/00/00/wKiAjVlpQVyARpQwAADGA0F72jo566.jpg");
    }
}

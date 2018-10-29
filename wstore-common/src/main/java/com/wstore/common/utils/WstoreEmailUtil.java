package com.wstore.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.chrono.MinguoChronology;

/**
 * 邮件发送工具
 * @ClassName WstoreEmailUtil
 * @Author Koi
 * @Date 2018/7/16 13:29
 * @Version 1.0
 * wstore1234
 */
public class WstoreEmailUtil {

    @Autowired
    JavaMailSenderImpl javaMailSender;

    public void registerMail(String receive, String code) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("【注册验证】欢迎注册Wstore");
            mimeMessageHelper.setText("<h3 style='color:#32a5e7;text-align: center;'>欢迎注册Wstore!</h3>" +
                    "</br><p>请输入验证码完成注册，您的验证码为"+code+"</p>");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

package com.wstore.common;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class WstoreCommonApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

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

}

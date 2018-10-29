package com.wstore.mail.service.impl;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName MailServiceImpl
 * @Author Koi
 * @Date 2018/7/19 16:05
 * @Version 1.0
 */
@Service
public class MailServiceImpl {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @RabbitListener(bindings=@QueueBinding(value=@Queue("wstore.mail.register"),exchange=@Exchange("mail.exchange")))
    public void registerMail(Map<String, Object> map) {

        String receive = map.get("receive").toString();
        String code = map.get("code").toString();

        System.out.println("debug:注册账号，向["+receive+"]发送验证邮件,验证码："+code);

        /*MimeMessage mimeMessage = mailSender.createMimeMessage();
        String mailContent = "<h3 style='color:#32a5e7;text-align: center;'>欢迎注册Wstore!</h3>" +
                "</br><h4>请输入验证码完成注册，您的验证码为 &nbsp;<b>"+code+"</b>&nbsp; 请在10分钟内完成验证</h4>";
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("【注册验证-Wstore】欢迎注册Wstore");
            mimeMessageHelper.setText(mailContent, true);
            mimeMessageHelper.setTo(receive);
            mimeMessageHelper.setFrom(username);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }

    @RabbitListener(bindings=@QueueBinding(value=@Queue("wstore.mail.find"),exchange=@Exchange("mail.exchange")))
    public void findUser(Map<String, Object> map){
        String receive = map.get("receive").toString();
        String code = map.get("code").toString();

        System.out.println("debug:修改密码：向["+receive+"]发送验证邮件,验证码："+code);

        /*MimeMessage mimeMessage = mailSender.createMimeMessage();
        String mailContent = "<h3 style='color:#32a5e7;text-align: center;'>密码修改Wstore!</h3>" +
                "</br><h3 style='color:#32a5e7;text-align: center;'>请输入验证码修改密码，您的验证码为 &nbsp;<b>"+code+"</b>&nbsp; 请在10分钟内完成验证</h3>";
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("【修改密码-Wstore】欢迎注册Wstore");
            mimeMessageHelper.setText(mailContent, true);
            mimeMessageHelper.setTo(receive);
            mimeMessageHelper.setFrom(username);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }*/
    }
}

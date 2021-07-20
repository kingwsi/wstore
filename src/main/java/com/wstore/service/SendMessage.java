package com.wstore.service;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;

/**
 * 发送短信验证
 *
 * @ClassName SendMessage
 * @Author Koi
 * @Date 2018/9/16 11:25
 * @Version 1.0
 */
@Service
public class SendMessage {

    static String requestUrl="http://api.feige.ee/SmsService/Send";

    static final String ACCOUNT = "17639206656";
    static final String KEY = "f41d32a036ff01034bd1ee3e5";
    static final String SIGHID = "48838";

    @RabbitListener(bindings=@QueueBinding(value=@Queue("wstore.message.register"),exchange=@Exchange("message.exchange")))
    public void sendMessage(Map<String, Object> map){
        String receive = map.get("receive").toString();
        String count = "您的验证码为："+map.get("code").toString()+"，10分钟内有效，请尽快验证。如非本人操作，请忽略本短信。";
        try {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("Account",ACCOUNT));
            formparams.add(new BasicNameValuePair("Pwd",KEY));
            formparams.add(new BasicNameValuePair("Content",count));
            formparams.add(new BasicNameValuePair("Mobile",receive));
            formparams.add(new BasicNameValuePair("SignId",SIGHID));
            Post(formparams);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void Post(List<NameValuePair> formparams) throws Exception {
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();

        httpClient.start();

        HttpPost requestPost = new HttpPost(requestUrl);

        requestPost.setEntity(new UrlEncodedFormEntity(formparams, "utf-8"));

        httpClient.execute(requestPost, new FutureCallback<HttpResponse>() {

            public void failed(Exception arg0) {

                System.out.println("Exception: " + arg0.getMessage());
            }

            public void completed(HttpResponse arg0) {
                System.out.println("Response: " + arg0.getStatusLine());
                try {

                    InputStream stram = arg0.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stram));
                    System.out.println(reader.readLine());

                } catch (UnsupportedOperationException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            public void cancelled() {
                // TODO Auto-generated method stub

            }
        }).get();
    }
}

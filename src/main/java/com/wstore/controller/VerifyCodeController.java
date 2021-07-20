package com.wstore.controller;

import com.wstore.common.utils.CookieUtils;
import com.wstore.common.utils.VerifyCodeUtils;
import com.wstore.common.utils.WstoreRandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 生成验证码，并将uid存储在cookie中
 * @ClassName ValidateCodeController
 * @Author Koi
 * @Date 2018/7/15 23:43
 * @Version 1.0
 */
@Controller
public class VerifyCodeController {

    private final static String VERIFY_CODE_KEY = "VERIFY:CODE_KEY_";

    private final static  Integer VERIFY_CODE_LENGTH = 4;

    private final static  Integer VERIFY_CODE_TIMEOUT = 300;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/verifyCode")
    public void getVerifyCode(HttpServletResponse response, HttpServletRequest request) throws IOException {

        String uid = VERIFY_CODE_KEY + WstoreRandomUtil.generateRandomString(32);
        String verifyCode = VerifyCodeUtils.generateVerifyCode(VERIFY_CODE_LENGTH);

        CookieUtils.setCookie(request, response,VERIFY_CODE_KEY,uid, VERIFY_CODE_TIMEOUT);

        stringRedisTemplate.opsForValue().set(uid,verifyCode,300, TimeUnit.SECONDS);

        int w = 120, h = 44;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }
}


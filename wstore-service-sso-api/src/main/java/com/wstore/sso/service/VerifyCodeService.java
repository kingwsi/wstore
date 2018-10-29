package com.wstore.sso.service;

/**
 * @ClassName VerifyCodeService
 * @Author Koi
 * @Date 2018/7/17 10:05
 * @Version 1.0
 */
public interface VerifyCodeService {

    public void generateVerifyRedis(String uuid, String verifyCode);

    public boolean checkVerifyCode(String uuid, String verifyCode);
}

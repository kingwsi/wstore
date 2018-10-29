package com.wstore.pojo.sso;

/**
 * 注册时封装的用户类,用做注册校验
 * @ClassName UserValidate
 * @Author Koi
 * @Date 2018/7/20 14:31
 * @Version 1.0
 */
public class UserValidate {

    private String verifyCode;

    private String userAccount;

    private String userName;

    private String password;

    private String repeatPassword;

    private String IPAddress;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }
}

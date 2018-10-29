package com.wstore.pojo.admin;

public class Admin {
    private Integer id;

    private String account;

    private String password;

    private String preIpAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPreIpAddress() {
        return preIpAddress;
    }

    public void setPreIpAddress(String preIpAddress) {
        this.preIpAddress = preIpAddress == null ? null : preIpAddress.trim();
    }
}
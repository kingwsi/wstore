package com.wstore.sso.service;

import com.wstore.common.utils.WstoreResultMsg;
import com.wstore.pojo.sso.UserValidate;
import com.wstore.pojo.sso.User;

public interface UserService {

    //登陆
    public WstoreResultMsg login(User user);

    /**
     * 注册检查
     * 邮箱/手机号 是否注册过
     * @param account 邮箱/手机号
     * @return
     */
    public WstoreResultMsg registerCheck(String account);

    /**
     * 验证用户是否存在
     * @param account 用户名/手机号/邮箱
     * @return true 存在， false 不存在
     */
    public User checkUserName(String account);

    /**
     * 注册请求
     * @param userValidate 封装的验证用户类
     * @return true 成功 false 失败
     */
    public Boolean register(UserValidate userValidate);

    /**
     * 更新账号信息
     * @param userValidate 封装的验证用户类
     * @return true 成功 false 失败
     */
    public Boolean updateUser(UserValidate userValidate);
}

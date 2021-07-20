package com.wstore.service;

import org.springframework.stereotype.Service;
import com.wstore.common.utils.*;
import com.wstore.mapper.UserMapper;
import com.wstore.pojo.sso.UserExample;
import com.wstore.pojo.sso.UserValidate;
import com.wstore.pojo.sso.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @ClassName UserServiceImpl
 * @Author Koi
 * @Date 2018/7/12 14:05
 * @Version 1.0
 */
@Service
public class UserService {

    private final static Integer SALT_LENGTH = 5;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;


    public WstoreResultMsg login(User user) {
        if(user == null || user.getUserName() == null){
            return WstoreResultMsg.status(400, "空值");
        }

        List<User> users = userMapper.selectUserByUser(user);

        if(users.size() == 0||users == null){
            return WstoreResultMsg.status(400, "账号不存在");
        }

        User checkUser = users.get(0);

        if(!checkUser.getUserPassword().equals(WstoreMD5Util.encoder(user.getUserPassword(), checkUser.getUserSalt()))){
            return WstoreResultMsg.status(405, "密码错误");
        }

        if(checkUser.getUserStatus() != 1){
            return WstoreResultMsg.status(405, "该账号暂时无法登录！请联系管理员");
        }

        return WstoreResultMsg.success().add("user",checkUser);
    }


    public WstoreResultMsg registerCheck(String account){
        if(!WstoreCheckUtil.isEmail(account) && !WstoreCheckUtil.isPhone(account)){
            return WstoreResultMsg.fail().add("error","账号不符合规范");
        }
        User user = new User();
        user.setUserName(account);
        List<User> users = userMapper.selectUserByUser(user);
        if(users.size() != 0 && users != null){
            return WstoreResultMsg.fail().add("error", "账号已存在");
        }
        return null;
    }


    public User checkUserName(String account) {
        User user = new User();
        user.setUserName(account);
        List<User> users = userMapper.selectUserByUser(user);
        if(users.size() > 0 && users != null){
            return users.get(0);
        }
        return null;
    }


    public Boolean register(UserValidate userValidate) {
        User user = new User();

        String salt = WstoreRandomUtil.generateRandomString(SALT_LENGTH);
        user.setUserPassword(WstoreMD5Util.encoder(userValidate.getPassword(), salt));

        user.setUserSalt(salt);

        if(WstoreCheckUtil.isEmail(userValidate.getUserAccount())){
            user.setUserEmail(userValidate.getUserAccount());
        }

        if(WstoreCheckUtil.isPhone(userValidate.getUserAccount())){
            user.setUserPhone(userValidate.getUserAccount());
        }
        user.setUserName(userValidate.getUserName());
        user.setUserStatus(1);
        user.setUserIp(userValidate.getIPAddress());
        user.setUserCreated(new Date());
        user.setUserUpdated(new Date());
        int insert = userMapper.insert(user);
        return insert > 0 ? true : false;
    }

    /**
     * 更新账号信息
     * @param userValidate 封装的验证用户类
     * @return true 成功 false 失败
     */

    public Boolean updateUser(UserValidate userValidate) {
        User user = new User();

        String salt = WstoreRandomUtil.generateRandomString(SALT_LENGTH);
        user.setUserPassword(WstoreMD5Util.encoder(userValidate.getPassword(), salt));
        user.setUserSalt(salt);
        user.setUserUpdated(new Date());

        UserExample example = new UserExample();

        if(WstoreCheckUtil.isEmail(userValidate.getUserAccount())){
           example.createCriteria().andUserEmailEqualTo(userValidate.getUserAccount());
        }

        if(WstoreCheckUtil.isPhone(userValidate.getUserAccount())){
            example.createCriteria().andUserPhoneEqualTo(userValidate.getUserAccount());
        }

        userMapper.updateByExampleSelective(user,example);
        return true;
    }
}

package com.wstore.config;

import com.wstore.common.utils.CookieUtils;
import com.wstore.common.utils.WstoreJsonUtil;
import com.wstore.pojo.sso.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName RedisUser
 * @Author wangshu
 * @Date 18-10-24 下午3:17
 * @Version 1.0
 */
@Component
public class RedisUser {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 根据cookie的uid查询用户
     *
     * @param request
     * @return User对象
     */
    public User redisToUser(HttpServletRequest request) {
        String uid = CookieUtils.getCookieValue(request, "UID");
        if (uid == null) {
            return null;
        }
        String userJson = stringRedisTemplate.opsForValue().get(uid);
        if (userJson == null) {
            return null;
        }
        return WstoreJsonUtil.fromJson(userJson, User.class);
    }
}

package com.wstore.controller;


import com.wstore.common.utils.CookieUtils;
import com.wstore.common.utils.WstoreRandomUtil;
import com.wstore.pojo.admin.Admin;
import com.wstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName CategoryController
 * @Author Koi
 * @Date 2018/7/25 16:26
 * @Version 1.0
 */
@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private final static String ADMIN_LOGIN_KEY = "LOGIN:ADMIN_KEY_";

    private final static Integer LOGIN_TIME_OUT = 600;

    @PostMapping("/admin")
    public String login(Admin admin,
                        Map<String, Object> map,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session){
        if (!adminService.login(admin)){
            map.put("msg","账号或密码错误，请重试！");
            return "login";
        }

        //存入Redis和Cookies
        String uid = WstoreRandomUtil.generateRandomString(32);
        CookieUtils.setCookie(request, response,"uid", uid, LOGIN_TIME_OUT);
        stringRedisTemplate.opsForValue().set(ADMIN_LOGIN_KEY + uid, admin.getAccount(),LOGIN_TIME_OUT);
        session.setAttribute("user",admin.getAccount());
        return "redirect:/index.html";
    }

    /**
     * 管理员退出登陆
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String loginOut(HttpServletRequest request){
        String uid = CookieUtils.getCookieValue(request, "uid");
        stringRedisTemplate.delete(ADMIN_LOGIN_KEY + uid);
        return "redirect:/index.html";
    }
}
package com.wstore.controller;


import com.wstore.common.utils.*;
import com.wstore.pojo.sso.UserValidate;
import com.wstore.pojo.sso.User;
import com.wstore.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class UserController {

    private final static String VERIFY_CODE_KEY = "VERIFY:CODE_KEY_";

    private final static Integer VERIFY_CODE_TIMEOUT = 600;

    private final static String USER_KEY = "SSO_KEY_";

    private final static Long USER_TIMEOUT = 300L;

    private final static String PORTAL_HOST = "http://localhost:8091";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 用户登陆
     *
     * @param user
     * @param verifyCode
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public WstoreResultMsg login(User user, String verifyCode, String href, HttpServletResponse response, HttpServletRequest request) {
        String verify_code = CookieUtils.getCookieValue(request, VERIFY_CODE_KEY);
        if (verify_code == null) {
            return WstoreResultMsg.fail().status(200, "页面已失效，请刷新重试！");
        }
        if (!stringRedisTemplate.opsForValue().get(verify_code).equalsIgnoreCase(verifyCode)) {
            return WstoreResultMsg.status(202, "验证码错误");
        }

        if (user == null || user.getUserName() == null) {
            return WstoreResultMsg.status(400, "空值");
        }

        //释放失效验证码
        stringRedisTemplate.delete(verify_code);

        WstoreResultMsg loginResult = userService.login(user);
        //登陆结果处理
        if (loginResult.getCode() == 100) {
            User sso = (User) loginResult.getExtend().get("user");
            String uid = CookieUtils.getCookieValue(request, USER_KEY);
            if (uid == null) {
                uid = USER_KEY + WstoreRandomUtil.generateRandomString(32);
                //将user对象以json格式存入redis
                CookieUtils.setCookie(request, response, USER_KEY, uid);
                stringRedisTemplate.opsForValue().set(uid, WstoreJsonUtil.toJson(sso), USER_TIMEOUT, TimeUnit.MINUTES);
            }
            //将通过验证的用户存入redis
            stringRedisTemplate.opsForValue().set(uid, WstoreJsonUtil.toJson(sso), USER_TIMEOUT, TimeUnit.MINUTES);
            if (null == href){
                return WstoreResultMsg.success().add("href",PORTAL_HOST);
            } else {
                return WstoreResultMsg.success().add("href",href);
            }
        }
        return loginResult;
    }

    @GetMapping("/register")
    public String regiser() {
        return "register";
    }

    @GetMapping("/find")
    public String find() {
        return "find";
    }

    /**
     * 找回密码 - 检查账号是否存在
     *
     * @param account
     * @param verifyCode
     * @param request
     * @return
     */
    @PostMapping("/findverify")
    @ResponseBody
    public WstoreResultMsg find(@RequestParam("account") String account,
                                @RequestParam("verifyCode") String verifyCode,
                                HttpServletRequest request) {

        String uid = CookieUtils.getCookieValue(request, "uid");
        if (!stringRedisTemplate.opsForValue().get(uid).equalsIgnoreCase(verifyCode)) {
            return WstoreResultMsg.status(202, "验证码错误");
        }

        if (userService.checkUserName(account) == null) {
            return WstoreResultMsg.fail().add("error", "账号不存在!");
        }

        String mail_verify = WstoreRandomUtil.generateRandomString(8).toUpperCase();

        //验证码存入Redis
        stringRedisTemplate.opsForValue().set(VERIFY_CODE_KEY + account, mail_verify, VERIFY_CODE_TIMEOUT);

        //发送验证邮件
        Map<String, Object> map = new HashMap<>();
        map.put("receive", account);
        map.put("code", mail_verify);
        rabbitTemplate.convertAndSend("mail.direct", "wstore.mail.update", map);

        return WstoreResultMsg.success();
    }

    /**
     * 修改用户
     *
     * @param userValidate
     * @param request
     * @return
     */
    @PutMapping("/user")
    @ResponseBody
    public WstoreResultMsg updateUser(UserValidate userValidate, HttpServletRequest request) {
        if (userValidate == null) {
            return WstoreResultMsg.fail().add("error", "空值");
        }

        if (stringRedisTemplate.opsForValue().get(VERIFY_CODE_KEY + userValidate.getUserAccount()).equalsIgnoreCase(userValidate.getVerifyCode())) {
            return WstoreResultMsg.fail().add("error", "验证码错误！");
        }

        if (!userValidate.getPassword().equals(userValidate.getRepeatPassword())) {
            return WstoreResultMsg.fail().add("error", "两次密码不一致！");
        }

        userValidate.setIPAddress(IpUtils.getIpAddr(request));

        if (!userService.updateUser(userValidate)) {
            return WstoreResultMsg.fail().add("error", "系统错误！请稍后重试");
        }
        stringRedisTemplate.delete(VERIFY_CODE_KEY + userValidate.getUserAccount());
        return WstoreResultMsg.success();
    }


    /**
     * 注册校验
     *
     * @param account
     * @param verifyCode
     * @param request
     * @return
     */
    @PostMapping("/verify/register")
    @ResponseBody
    public WstoreResultMsg verifyRegister(@RequestParam("account") String account,
                                          @RequestParam("verifyCode") String verifyCode,
                                          HttpServletRequest request) {

        String uid = CookieUtils.getCookieValue(request, "uid");

        if (!stringRedisTemplate.opsForValue().get(uid).equalsIgnoreCase(verifyCode)) {
            return WstoreResultMsg.status(202, "验证码错误");
        }

        stringRedisTemplate.delete(uid);

        //校验账号是否可注册
        WstoreResultMsg check_result = userService.registerCheck(account);
        if (check_result != null) {
            return check_result;
        }

        if (stringRedisTemplate.opsForValue().get("SEND_FLAG" + account) != null) {
            Long expire = stringRedisTemplate.getExpire("SEND_FLAG" + account, TimeUnit.SECONDS);
            return WstoreResultMsg.fail().add("error", "操作太快了，请" + expire + "秒后再试");
        }
        String mail_verify = WstoreRandomUtil.generateRandomString(8).toUpperCase();
        stringRedisTemplate.opsForValue().set(VERIFY_CODE_KEY + account, mail_verify, 600);
        Map<String, Object> map = new HashMap<>();
        map.put("receive", account);
        map.put("code", mail_verify);

        //短信注册
        if (WstoreCheckUtil.isPhone(account)) {
            //return WstoreResultMsg.fail().add("error", "暂不支持手机号注册");
            System.out.println("注册验证短信发送：" + account + " -- " + mail_verify);
            rabbitTemplate.convertAndSend("message.exchange", "wstore.message.register", map);
            stringRedisTemplate.opsForValue().set("SEND_FLAG" + account, account, 60, TimeUnit.SECONDS);
            return WstoreResultMsg.success();
        }
        //邮箱注册
        if (WstoreCheckUtil.isEmail(account)) {
            System.out.println("注册验证邮件发送：" + account + " -- " + mail_verify);
            rabbitTemplate.convertAndSend("mail.exchange", "wstore.mail.register", map);
            //设置同一邮箱账号邮件发送间隔
            stringRedisTemplate.opsForValue().set("SEND_FLAG" + account, account, 60, TimeUnit.SECONDS);
            return WstoreResultMsg.success();
        }
        return WstoreResultMsg.fail();
    }

    /**
     * 注册账号
     *
     * @return 100注册成功 200注册失败
     */
    @PostMapping("/register")
    @ResponseBody
    public WstoreResultMsg register(UserValidate userValidate, HttpServletRequest request) {
        if (userValidate == null) {
            return WstoreResultMsg.fail().add("error", "空值");
        }

        String verify_code = stringRedisTemplate.opsForValue().get(VERIFY_CODE_KEY + userValidate.getUserAccount());

        if (!verify_code.trim().equals(userValidate.getVerifyCode().trim())) {
            System.out.println(userValidate.getVerifyCode());
            System.out.println(verify_code);
            return WstoreResultMsg.fail().add("error", "验证码错误！");
        }

        if (!userValidate.getPassword().equals(userValidate.getRepeatPassword())) {
            return WstoreResultMsg.fail().add("error", "两次密码不一致！");
        }

        if (userService.checkUserName(userValidate.getUserName()) != null) {
            return WstoreResultMsg.fail().add("error", "用户名已存在！");
        }

        userValidate.setIPAddress(IpUtils.getIpAddr(request));

        if (!userService.register(userValidate)) {
            WstoreResultMsg.fail().add("error", "注册失败！");
        }

        stringRedisTemplate.delete(VERIFY_CODE_KEY + userValidate.getUserAccount());

        return WstoreResultMsg.success();
    }

    @GetMapping("/signout")
    public String signOut(HttpServletRequest request) {
        String uid = CookieUtils.getCookieValue(request, USER_KEY);
        if (uid==null){
            return "redirect:login";
        }
        stringRedisTemplate.delete(uid);
        return "redirect:/login";
    }
}

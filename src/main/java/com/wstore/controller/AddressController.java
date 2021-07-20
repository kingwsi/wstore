package com.wstore.controller;


import com.wstore.common.utils.CookieUtils;
import com.wstore.common.utils.WstoreJsonUtil;
import com.wstore.common.utils.WstoreResultMsg;
import com.wstore.config.RedisUser;
import com.wstore.pojo.order.Address;
import com.wstore.pojo.sso.User;
import com.wstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 地址管理
 * @ClassName AddressController
 * @Author wangshu
 * @Date 18-9-27 上午11:00
 * @Version 1.0
 */
@Controller
public class AddressController {
    @Autowired
    AddressService addressService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisUser redisUser;

    /**
     * JSON debug
     *
     * @return
     */
    @GetMapping("/address/json")
    @ResponseBody
    public Object testJson() {
        List<Address> addresses = addressService.getAddresses(13);
        return addresses;
    }

    /**
     * 地址编辑回显
     * @param address
     * @param request
     * @return
     */
    @GetMapping("/user/address")
    @ResponseBody
    public WstoreResultMsg getAddress(Address address, HttpServletRequest request) {
        address.setUserId(redisUser.redisToUser(request).getUserId());
        Address resultAddress = addressService.getAddress(address);
        return WstoreResultMsg.success().add("address", resultAddress);
    }

    @PostMapping("/default/address")
    @ResponseBody
    public WstoreResultMsg setDefault(Address address, HttpServletRequest request){
        address.setUserId(redisUser.redisToUser(request).getUserId());
        address.setIsDefault(1);
        addressService.updateToDefault(address);
        return WstoreResultMsg.success();
    }

    /**
     * 新增地址
     *
     * @param address
     * @param request
     * @return
     */
    @PostMapping("/address")
    @ResponseBody
    public WstoreResultMsg addAddress(Address address, HttpServletRequest request) {
        String uid = CookieUtils.getCookieValue(request, "UID");
        if (uid == null) {
            return WstoreResultMsg.fail().add("error", "非法请求！");
        }
        String userJson = stringRedisTemplate.opsForValue().get(uid);
        if (userJson == null) {
            return WstoreResultMsg.fail().add("error", "非法请求！");
        }
        User user = WstoreJsonUtil.fromJson(userJson, User.class);
        address.setUserId(user.getUserId());
        //校验地址是否合法
        WstoreResultMsg checkMsg = checkAddress(address);
        if (checkMsg.getCode() != 100) {
            return checkMsg;
        }
        addressService.createAddress(address);
        return WstoreResultMsg.success();
    }

    /**
     * 地址更新方法
     * @param address
     * @param request
     * @return
     */
    @PutMapping("/address")
    @ResponseBody
    public WstoreResultMsg updateAddress(Address address, HttpServletRequest request) {
        address.setUserId(redisUser.redisToUser(request).getUserId());
        addressService.updateAddress(address);
        return WstoreResultMsg.success();
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    /**
     * 删除用户地址
     * @param id 用户id
     * @param request
     * @return
     */
    @DeleteMapping("/address/{id}")
    @ResponseBody
    public WstoreResultMsg deleteAddress(@PathVariable("id") Integer id, HttpServletRequest request){
        User user = redisUser.redisToUser(request);
        Address address = new Address();
        address.setId(id);
        address.setUserId(user.getUserId());
        addressService.deleteAddress(address);
        return WstoreResultMsg.success();
    }

    /**
     * 地址校验方法
     *
     * @param address
     * @return 501收货人不合法 502手机号不合法  503地址不合法
     */
    public WstoreResultMsg checkAddress(Address address) {
        if (address.getReceiver() == null) {
            return WstoreResultMsg.status(501, "请输入收货人名称！");
        }
        if (address.getPhone() == null) {
            return WstoreResultMsg.status(502, "请输入手机号！");
        }
        if (address.getAddress() == null) {
            return WstoreResultMsg.status(503, "请输入收获地址！");
        }
        if (address.getReceiver().length() < 2 || address.getReceiver().length() > 10) {
            return WstoreResultMsg.status(501, "请输入2-10位收货人名称！");
        }
        if (address.getPhone().length() != 11) {
            return WstoreResultMsg.status(502, "请输入11位手机号！");
        }
        if (address.getAddress().length() < 5 || address.getAddress().length() > 30) {
            return WstoreResultMsg.status(503, "请输入完整的收货地址！（5-30个字以内）");
        }
        if (address.getIsDefault() == null) {
            address.setIsDefault(0);
        }
        return WstoreResultMsg.success();
    }
}

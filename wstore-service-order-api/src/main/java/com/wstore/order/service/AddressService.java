package com.wstore.order.service;

import com.wstore.pojo.order.Address;

import java.util.List;

/**
 * @ClassName AddressService
 * @Author Koi
 * @Date 2018/9/20 9:07
 * @Version 1.0
 */
public interface AddressService {
    /**
     * 根据用户id查询用户所有收货地址
     *
     * @param uid 用户id
     * @return
     */
    List<Address> getAddresses(Integer uid);

    /**
     * 获取用户收货地址
     *
     * @param address
     * @return
     */
    Address getAddress(Address address);

    /**
     * 根据用户id更新用户收货地址
     *
     * @param address
     */
    void updateAddress(Address address);

    /**
     * 根据用户id删除收货地址
     *
     * @param address
     */
    void deleteAddress(Address address);

    /**
     * 新建用户收货地址
     *
     * @param address
     */
    void createAddress(Address address);

    /**
     * 将地址设置为默认地址
     * @param address
     */
    void updateToDefault(Address address);
}

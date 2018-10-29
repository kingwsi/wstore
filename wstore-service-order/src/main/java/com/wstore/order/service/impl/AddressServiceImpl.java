package com.wstore.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wstore.mapper.AddressMapper;
import com.wstore.pojo.order.Address;
import com.wstore.pojo.order.AddressExample;
import com.wstore.order.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AddressServiceImpl
 * @Author Koi
 * @Date 2018/9/20 10:12
 * @Version 1.0
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressMapper addressMapper;

    /**
     * 根据用户id查询收货地址
     *
     * @param uid 用户id
     * @return
     */
    @Override
    public List<Address> getAddresses(Integer uid) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(uid);
        example.setOrderByClause("is_default DESC");
        return addressMapper.selectByExample(example);
    }

    @Override
    public Address getAddress(Address address) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(address.getUserId()).andIdEqualTo(address.getId());
        return addressMapper.selectByExample(example).get(0);
    }

    /**
     * 根据用户id更新用户收货地址
     *
     * @param address
     */
    @Override
    public void updateAddress(Address address) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(address.getUserId())
                .andIdEqualTo(address.getId());
        address.setUpdateTime(new Date());
        addressMapper.updateByExampleSelective(address, example);
    }

    /**
     * 根据用户id删除收货地址
     *
     * @param address
     */
    @Override
    public void deleteAddress(Address address) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(address.getUserId())
                .andIdEqualTo(address.getId());
        addressMapper.deleteByExample(example);
    }

    /**
     * 新建用户收货地址
     * 新增收货地址之前先进行检查
     *
     * @param address
     */
    @Override
    public void createAddress(Address address) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(address.getUserId()).andIsDefaultEqualTo(1);
        if (addressMapper.selectByExample(example).size() < 1) {
            address.setIsDefault(1);
        }
        address.setUpdateTime(new Date());
        address.setCreateTime(new Date());
        addressMapper.insertSelective(address);
    }

    @Transactional
    @Override
    public void updateToDefault(Address address) {
        AddressExample example = new AddressExample();
        example.createCriteria().andUserIdEqualTo(address.getUserId());
        //将该用户所有地址标识设置为0
        Address address1 = new Address();
        address1.setIsDefault(0);
        addressMapper.updateByExampleSelective(address1,example);
        this.updateAddress(address);
    }
}

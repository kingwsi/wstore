package com.wstore.service;

import org.springframework.stereotype.Service;
import com.wstore.mapper.AdminMapper;
import com.wstore.mapper.CategoryMapper;
import com.wstore.mapper.CategorySecondaryMapper;
import com.wstore.pojo.admin.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName AdminServiceImpl
 * @Author Koi
 * @Date 2018/7/26 14:49
 * @Version 1.0
 */
@Service
public class AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategorySecondaryMapper categorySecondaryMapper;


    public boolean login(Admin admin) {
        AdminExample example = new AdminExample();
        example.createCriteria().andAccountEqualTo(admin.getAccount())
        .andPasswordEqualTo(admin.getPassword());
        List<Admin> admins = adminMapper.selectByExample(example);
        if(admins.size()<1 || admins == null){
            return false;
        }
        return true;
    }
}

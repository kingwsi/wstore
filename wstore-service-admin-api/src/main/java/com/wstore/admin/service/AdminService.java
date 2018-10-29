package com.wstore.admin.service;

import com.wstore.pojo.admin.Admin;
import com.wstore.pojo.admin.Category;
import com.wstore.pojo.admin.CategorySecondary;

import java.util.List;

/**
 * @ClassName AdminLogin
 * @Author Koi
 * @Date 2018/7/26 14:42
 * @Version 1.0
 */
public interface AdminService {
    public boolean login(Admin admin);

}

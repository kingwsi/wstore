package com.wstore.admin.service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.wstore.mapper")
@DubboComponentScan(basePackages = "com.wstore.admin.service.impl")
@EnableTransactionManagement
public class WstoreServiceAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreServiceAdminApplication.class, args);
    }
}

package com.wstore.sso.service;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wstore.mapper")
@DubboComponentScan(basePackages ="com.wstore.sso.service.impl")
public class WstoreServiceSsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WstoreServiceSsoApplication.class, args);
    }
}

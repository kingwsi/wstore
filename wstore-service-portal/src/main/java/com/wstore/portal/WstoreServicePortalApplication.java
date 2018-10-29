package com.wstore.portal;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wstore.mapper")
@DubboComponentScan(basePackages = "com.wstore.portal.service.impl")
public class WstoreServicePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreServicePortalApplication.class, args);
    }
}

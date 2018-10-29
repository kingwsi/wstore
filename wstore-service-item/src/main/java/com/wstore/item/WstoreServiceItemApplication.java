package com.wstore.item;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wstore.mapper")
@DubboComponentScan(basePackages = "com.wstore.item.service.impl")
public class WstoreServiceItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreServiceItemApplication.class, args);
    }
}

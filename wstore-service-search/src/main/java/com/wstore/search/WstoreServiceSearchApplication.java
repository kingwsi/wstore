package com.wstore.search;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.wstore.search.service.impl")
@MapperScan("com.wstore.mapper")
public class WstoreServiceSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreServiceSearchApplication.class, args);
    }
}

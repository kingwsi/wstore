package com.wstore.order;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wstore.mapper")
@DubboComponentScan(basePackages = "com.wstore.order.service.impl")
public class WstoreServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(WstoreServiceOrderApplication.class, args);
    }
}

package com.wstore.order;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.wstore.order.service")
public class WstoreWebOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreWebOrderApplication.class, args);
    }
}

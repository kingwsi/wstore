package com.wstore.pay;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wstore.mapper")
@DubboComponentScan(basePackages = "com.wstore.pay.service.impl")
public class WstoreServicePayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WstoreServicePayApplication.class, args);
	}
}

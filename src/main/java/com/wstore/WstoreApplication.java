package com.wstore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;


@SpringBootApplication
@MapperScan(basePackages = "com.wstore.mapper")
public class WstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreApplication.class, args);
    }
}

package com.wstore.admin;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

@SpringBootApplication
@DubboComponentScan(basePackages = "com.wstore.admin.service")
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class WstoreWebAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreWebAdminApplication.class, args);
    }
}

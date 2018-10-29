package com.wstore.sso;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class WstoreWebSsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(WstoreWebSsoApplication.class, args);
    }
}

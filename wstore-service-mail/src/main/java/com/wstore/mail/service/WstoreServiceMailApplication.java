package com.wstore.mail.service;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class WstoreServiceMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(WstoreServiceMailApplication.class, args);
    }
}

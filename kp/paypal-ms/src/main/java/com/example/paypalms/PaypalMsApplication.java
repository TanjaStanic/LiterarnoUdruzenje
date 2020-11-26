package com.example.paypalms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaypalMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaypalMsApplication.class, args);
    }

}

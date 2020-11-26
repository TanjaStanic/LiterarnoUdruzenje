package com.example.bitcoinms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BitcoinMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BitcoinMsApplication.class, args);
    }

}

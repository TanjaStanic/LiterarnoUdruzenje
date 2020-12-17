package com.example.paypalms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.ZonedDateTime;

@SpringBootApplication
@EnableDiscoveryClient
public class PaypalMsApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(PaypalMsApplication.class, args);
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/").resourceChain(false);
    }

}

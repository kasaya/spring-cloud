package com.oycl;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.oycl"})
public class InterfaceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceServiceApplication.class, args);
    }
}
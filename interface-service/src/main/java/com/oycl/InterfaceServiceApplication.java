package com.oycl;

import com.oycl.compment.db.annotation.EnabelBaseMapper;
import com.oycl.compment.log.annotation.EnableCustomLog;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@ComponentScan(basePackages = {"com.oycl"})
@EnableCustomLog
@EnableFeignClients
@EnabelBaseMapper
public class InterfaceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceServiceApplication.class, args);
    }
}

package com.oycl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@RemoteApplicationEventScan(basePackages = "com.oycl.dynamicroutes")
public class SpringGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGatewayServiceApplication.class, args);
	}

}

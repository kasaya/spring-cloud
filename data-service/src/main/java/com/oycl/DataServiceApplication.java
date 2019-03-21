package com.oycl;

import com.oycl.compment.db.annotation.EnabelBaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.oycl"})
@EnableDiscoveryClient
@EnabelBaseMapper
@MapperScan(basePackages = "com.oycl.dao")
public class DataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataServiceApplication.class, args);
	}

}

package com.oycl.compment.http.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestHttpCallConfig {

    @LoadBalanced
    @Bean
    public RestTemplate restHttpCallRestTemplate(){
        return new RestTemplate();
    }


}

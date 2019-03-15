package com.oycl.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        String username = "kasaya"; String password = "kasaya";
        return new BasicAuthRequestInterceptor(username,password);
    }

}

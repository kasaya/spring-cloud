package com.oycl.starter.autoconfiguration;

import com.oycl.starter.properties.ExceptionAutoConfigProperty;
import com.oycl.starter.service.ExceptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "exception", name = "errorMap")
@EnableConfigurationProperties(ExceptionAutoConfigProperty.class)
public class ExceptionHandlerAutoConfiguration {

    @Autowired
    ExceptionAutoConfigProperty exceptionAutoConfigProperty;

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerAutoConfiguration.class);

    @Bean
    public ExceptionService exceptionService(){
        ExceptionService exceptionService = new ExceptionService(exceptionAutoConfigProperty);
        return exceptionService;
    }

}

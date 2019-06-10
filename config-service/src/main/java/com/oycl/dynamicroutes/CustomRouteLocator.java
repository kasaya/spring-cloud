package com.oycl.dynamicroutes;

import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通过bean形式配置路径
 */
@Configuration
public class CustomRouteLocator {

    @Bean
    public DynamicRouteBusEndpoint refreshRoutesEndpoint(ApplicationContext applicationContext, BusProperties bus) {
        return new DynamicRouteBusEndpoint(applicationContext, bus.getId());
    }
}

package com.oycl.config;

import com.oycl.dynamicroutes.DynamicRouteBusEndpoint;
import com.oycl.filiter.JwtFiliter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
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

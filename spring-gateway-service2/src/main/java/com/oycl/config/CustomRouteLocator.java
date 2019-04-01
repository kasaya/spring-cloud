package com.oycl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDiscoveryClient
public class CustomRouteLocator {

    @Autowired
    private TokenRelayGatewayFilterFactory filterFactory;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 自定义路径 /infc/
                .route("interface-service",
                        r->r.path("/**/infc/**")
                                .filters(f->f.rewritePath("[^\\s]*/infc/(?<segment>.*)","/$\\{segment}").filter(filterFactory.apply()))
                        .uri("lb://interface-service"))
                .build();

    }

}

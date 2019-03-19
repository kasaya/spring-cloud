package com.oycl.config;

import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableDiscoveryClient
public class CustomRouteLocator {


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 自定义路径 /infc/
                .route("interface-service", r->r.path("/**/infc/**").filters(f->f.rewritePath("[^\\s]*/infc/(?<segment>.*)","/$\\{segment}"))
                        .uri("lb://interface-service"))
                .build();

    }



    @Bean
    public MapReactiveUserDetailsService reactiveUserDetailsService() {

        UserDetails user = User.withDefaultPasswordEncoder().username("kasaya").password("kasaya").roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }




}

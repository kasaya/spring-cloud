package com.oycl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtSecurityContextRepository jwtSecurityContextRepository;





    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                //.securityContextRepository(jwtSecurityContextRepository)
//                .authorizeExchange()
//                .pathMatchers(".*/login").permitAll()
//                .pathMatchers("/oauth/**").permitAll()
//                .anyExchange().authenticated()
//                .and()
                .build();
    }
}

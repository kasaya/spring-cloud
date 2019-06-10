package com.oycl.compment.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置httpbasic security
 */

@Configuration
public class CommonSecurityConfig {

    @Configuration
    protected static class MatcherConfiguration {
        @Configuration
        @ConditionalOnProperty(value = "spring.customize.globalsecurity.enabled", havingValue = "true")
        @EnableWebSecurity
        @EnableGlobalMethodSecurity(jsr250Enabled = true)
        public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


            @Override
            protected void configure(HttpSecurity http) throws Exception {

                http
                        .csrf().disable()
                        .formLogin().disable()
                        .httpBasic().disable();
                //.authorizeRequests()
                //.anyRequest().authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterAfter(new JwtTokenAuthenticationFilter(),
//                        UsernamePasswordAuthenticationFilter.class);
            }
        }
    }

}

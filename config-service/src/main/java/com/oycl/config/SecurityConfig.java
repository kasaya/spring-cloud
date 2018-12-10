package com.oycl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表示所有的访问都必须进行认证处理后才可以正常进行

        http
                .csrf().disable() //Spring Security 4.0之后，引入了CSRF，默认是开启,如果是用第三方进行调用的话，则会被csrf拦截，已403错误返回
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();


        // 所有的Rest服务一定要设置为无状态，以提升操作性能
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

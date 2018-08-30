package cango.scf.com.config;

import cango.scf.com.filter.JwtTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**定义认证用户信息获取来源，密码校验规则等*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

      http
              //Spring Security 4.0之后，引入了CSRF，默认是开启,如果是用第三方进行调用的话，则会被csrf拦截，已403错误返回
              .csrf().disable()
              .formLogin().disable()
              .httpBasic().disable()
              .authorizeRequests()
              //定义/login请求不需要验证
              .regexMatchers(".*/login").permitAll()
              .anyRequest().authenticated()
              .and()
              //定义logout不需要验证
              .logout().permitAll()
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .addFilterAfter(new JwtTokenAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }

}

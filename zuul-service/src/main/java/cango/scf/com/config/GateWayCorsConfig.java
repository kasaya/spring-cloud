package cango.scf.com.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayCorsConfig {

    /****
     *
     * corsFilter 跨域，但是不清楚 对https请求貌似有问题，在生产环境中，443端口会拦截
     * 因此不使用
     *
     * ****/
   /* @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("X-forwared-port, X-forwarded-host");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }*/
}

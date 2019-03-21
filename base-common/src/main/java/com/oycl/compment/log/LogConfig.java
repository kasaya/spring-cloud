package com.oycl.compment.log;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@ConditionalOnClass(Aspect.class)
@EnableAspectJAutoProxy
@Import({LogProcess.class, DbLogProcess.class})
public class LogConfig {

    /**
     * 注入处理类
     * @return
     */
    @Bean
    public ILogManager getLogManager(){
        return DefaultLogManager.INSTANCE;
    }
}

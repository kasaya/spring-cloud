package com.oycl.compment.log.partner;

import com.oycl.compment.log.manager.DefaultLogManager;
import com.oycl.compment.log.process.LogProcess;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

/**
 * 自定义log配置类
 */
@ConditionalOnClass(Aspect.class)
@Configuration
@EnableAspectJAutoProxy
@Import({LogProcess.class})
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

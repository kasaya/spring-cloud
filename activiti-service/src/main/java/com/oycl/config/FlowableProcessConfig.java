package com.oycl.config;

import org.flowable.common.engine.impl.history.HistoryLevel;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

import static org.flowable.common.engine.impl.AbstractEngineConfiguration.DB_SCHEMA_UPDATE_TRUE;

@Configuration
public class FlowableProcessConfig {


    /*protected ResourcePatternResolver resourcePatternResolver;

    public FlowableProcessConfig(ResourcePatternResolver resourcePatternResolver){
        this.resourcePatternResolver = resourcePatternResolver;
    }

    @Bean
    @Qualifier("processEngineConfiguration")
    public SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource, PlatformTransactionManager manager){
        SpringProcessEngineConfiguration configuration =  new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(manager);
        configuration.setAsyncExecutorActivate(true);
        configuration.setHistoryLevel(HistoryLevel.AUDIT);
        configuration.setDatabaseSchema("flowable");
        configuration.setDbHistoryUsed(true);
        configuration.setDatabaseSchemaUpdate(DB_SCHEMA_UPDATE_TRUE);
        configuration.setEnableDatabaseEventLogging(true);
        configuration.setLabelFontName("simsun");
        configuration.setActivityFontName("simsun");
        configuration.setAnnotationFontName("simsun");
        configuration.setDisableIdmEngine(false);


        Resource[] resources = null;
        try {
            resources = resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+"/processes/*.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resources != null && resources.length != 0) {
            configuration.setDeploymentResources(resources);
            configuration.setDeploymentName("flowableService");
        }
        return configuration;
    }

    @Bean
    @Qualifier("processEngine")
    public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration processEngineConfiguration){
        ProcessEngineFactoryBean engineFactoryBean = new ProcessEngineFactoryBean();
        engineFactoryBean.setProcessEngineConfiguration(processEngineConfiguration);
        return engineFactoryBean;
    }*/

}

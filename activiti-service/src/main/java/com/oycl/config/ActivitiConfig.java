package com.oycl.config;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ActivitiConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;


    @Bean
    @Primary
    public ProcessEngine processEngineConfiguration() {

        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();

        configuration.setDataSource(dataSource);

        configuration.setTransactionManager(platformTransactionManager);

        configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        configuration.setDatabaseSchema("test");

        configuration.setAsyncExecutorActivate(false);

        return configuration.buildProcessEngine();

    }

}

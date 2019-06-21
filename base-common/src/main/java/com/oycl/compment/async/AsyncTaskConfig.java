package com.oycl.compment.async;

import org.springframework.context.annotation.Bean;

public class AsyncTaskConfig {

    @Bean
    public BlockingTaskManager createBlockTaskManager(){
        BlockingTaskManager manager = new BlockingTaskManager();
        AsyncUtil.setBlockingTaskManager(manager);
        return manager;
    }
}

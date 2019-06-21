package com.oycl.compment.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;

/**
 * 任务管理类
 *
 *  @author oycl
 */
public class BlockingTaskManager extends BlockingTask {

    private final static Logger LOGGER = LoggerFactory.getLogger(BlockingTaskManager.class);
    /**
     * 可以设置CPU*2的
     */
    private final static int THREAD_SIZE = 8;

    public BlockingTaskManager() {
        //设置线程数量
        this.setTc(THREAD_SIZE);
        //启动消费线程
        this.start();

        LOGGER.info("BlockingTaskManager initialized");
    }

    @PreDestroy
    public void destory() {
        LOGGER.info("BlockingTaskManager shutdown");
        this.shutdown();
    }
}


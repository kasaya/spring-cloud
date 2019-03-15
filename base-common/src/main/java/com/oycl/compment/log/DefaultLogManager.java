package com.oycl.compment.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogManager implements ILogManager {


    public static final Logger logger = LoggerFactory.getLogger(DefaultLogManager.class);

    public static final DefaultLogManager INSTANCE = new DefaultLogManager();

    @Override
    public void dealLog(LogModel logModel) {
        logger.info("*******调用类：{} || 方法：{} || 执行时间：{}s || 参数：‘{}’ ********"
                , logModel.getModule()
                , logModel.getEvent()
                , logModel.getCreateDate()
                , logModel.getOptContent()
        );
    }
}

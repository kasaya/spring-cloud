package com.oycl.compment.log.manager;


import com.oycl.compment.log.partner.ILogManager;
import com.oycl.compment.log.partner.LogModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认log处理类
 */
public class DefaultLogManager implements ILogManager {


    public static final Logger logger = LoggerFactory.getLogger(DefaultLogManager.class);

    public static final DefaultLogManager INSTANCE = new DefaultLogManager();

    @Override
    public void dealLog(LogModel logModel) {
        if(logger.isDebugEnabled()){
            logger.debug("*******调用类：{} || 方法：{} || 执行时间：{}s || 参数：‘{}’ ********"
                    , logModel.getModule()
                    , logModel.getEvent()
                    , logModel.getCreateDate()
                    , logModel.getOptContent()
            );
        }else if(logger.isInfoEnabled()){
            logger.info("*******调用类：{} || 方法：{} || 执行时间：{}s  ********"
                    , logModel.getModule()
                    , logModel.getEvent()
                    , logModel.getCreateDate()
            );
        }
    }
}

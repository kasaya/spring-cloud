package com.oycl.compment.log;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

@Aspect
public class DbLogProcess {

    @Autowired
    Gson gson;

    @Autowired
    private ILogManager logManager;


    @Pointcut("execution(* com.oycl..*Mapper.*(..))")
    public void wlogPoint(){}

    @Around("wlogPoint()")
    public Object aroundwLogPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        LogModel logModel = new LogModel();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        logModel.setCreateDate(stopWatch.getTotalTimeSeconds());
        logModel.setEvent(joinPoint.getSignature().getName());
        logModel.setModule(joinPoint.getSignature().getDeclaringTypeName());
        logModel.setStatus("完成");
        dealLog(logModel);

        return result;

    }


    /**
     * 处理Log
     * @param logModel
     */
    private void dealLog(LogModel logModel){
        if(logManager == null){
            logManager = DefaultLogManager.INSTANCE;
        }
        logManager.dealLog(logModel);
    }

}

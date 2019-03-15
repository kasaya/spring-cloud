package com.oycl.compment.log;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
public class LogProcess {

    @Autowired
    Gson gson;

    @Autowired
    private ILogManager logManager;


    @Pointcut("execution(* com.oycl..*Controller.*(..)) " +
            "|| execution(* com.oycl..*Service.*(..)) " +
            "|| execution(* com.oycl..*Api.*(..)) " +
            "|| execution(* *.*Exception*(..))")
    public void wlogPoint(){}

    @Around("wlogPoint()")
    public Object aroundwLogPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        //是否可以用
        Class target= joinPoint.getSignature().getDeclaringType();

        if(!LogEnable.class.isAssignableFrom(target) && !LogEnable.class.isInstance(target)){
            return joinPoint.proceed();
        }
        //取得调用的方法
        Method method = getInvokedMethod(joinPoint);
        if(Objects.isNull(method)){
            return joinPoint.proceed();
        }

        LogModel logModel = new LogModel();
        logModel.setOptContent(getParam(joinPoint));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();
        logModel.setCreateDate(stopWatch.getTotalTimeSeconds());
        logModel.setEvent(method.getName());
        logModel.setModule(target.getName());
        logModel.setStatus("完成");
        dealLog(logModel);

        return result;

    }

    /**
     * 获取请求方法
     * @param joinPoint
     * @return
     */
    private Method getInvokedMethod(JoinPoint joinPoint){
      Class[] cLasses = Arrays.stream(joinPoint.getArgs()).map(obj->obj.getClass()).collect(Collectors.toList()).toArray(new Class[0]);
      String methodName = joinPoint.getSignature().getName();
      Method method = null;
        try {
            method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, cLasses);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    /**
     * 取得全部参数
     * @param joinPoint
     * @return
     */
    private String getParam(JoinPoint joinPoint){
        //","串联所有参数（json体）
        return Arrays.stream(joinPoint.getArgs()).map(obj->gson.toJson(obj)).collect(Collectors.joining(","));
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

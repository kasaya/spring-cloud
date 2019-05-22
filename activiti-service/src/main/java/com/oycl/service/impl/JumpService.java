package com.oycl.service.impl;


import com.google.gson.JsonObject;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Service
public class JumpService implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private Expression classname;
    private Expression function;
    private Expression param;

    public void setClassname(Expression classname) {
        this.classname = classname;
    }

    public void setFunction(Expression function) {
        this.function = function;
    }

    public void setParam(Expression param) {
        this.param = param;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) {
        logger.debug("****doSomeThing****");
        String params = (String) param.getValue(delegateExecution);
        Map<String, VariableInstance> map = delegateExecution.getVariableInstances(Arrays.asList(params.split(",")));
        JsonObject jsonObject = new JsonObject();
        map.entrySet().stream().forEach(item->{
            jsonObject.addProperty(item.getKey(), String.valueOf(item.getValue().getValue()));
            jsonObject.addProperty("processInstanceId", item.getValue().getProcessInstanceId());
        });


    }
}

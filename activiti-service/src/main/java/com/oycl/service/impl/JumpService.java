package com.oycl.service.impl;


import com.google.gson.JsonObject;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

@Service
public class JumpService implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    private Expression restUrl;
    private Expression param;

    public void setRestUrl(Expression restUrl) {
        this.restUrl = restUrl;
    }
    public void setParam(Expression param) {
        this.param = param;
    }


    @Override
    public void execute(DelegateExecution delegateExecution) {

        logger.debug("****doSomeThing****");
        String params = (String) param.getValue(delegateExecution);
        String strUrl = (String) restUrl.getValue(delegateExecution);
        Map<String, VariableInstance> map = delegateExecution.getVariableInstances(Arrays.asList(params.split(",")));
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("processInstanceId", delegateExecution.getProcessDefinitionId());
        jsonObject.addProperty("businessKey", delegateExecution.getProcessInstanceBusinessKey());

        map.entrySet().stream().forEach(item->{

            if(item.getValue()!= null && !item.getValue().equals("null")){
                jsonObject.addProperty(item.getKey(),String.valueOf(item.getValue().getValue()));
            }
        });


        String response = restTemplate.postForObject(URI.create(strUrl),jsonObject.toString(), String.class);
        //TODO:是否成功
        logger.debug(response);

    }
}

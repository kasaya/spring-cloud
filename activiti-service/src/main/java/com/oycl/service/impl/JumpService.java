package com.oycl.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JumpService implements JavaDelegate {
    private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    @Override
    public void execute(DelegateExecution delegateExecution) {
        logger.debug("****doSomeThing****");
        delegateExecution.getProcessDefinitionId();
    }
}

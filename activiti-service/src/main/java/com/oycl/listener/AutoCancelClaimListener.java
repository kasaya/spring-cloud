package com.oycl.listener;

import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutoCancelClaimListener implements TaskListener {

    @Autowired
    ManagementService managementService;

    @Autowired
    TaskService runtimeService;

    @Autowired
    ProcessEngineConfiguration processEngineConfiguration;




    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setVariable("taskId",delegateTask.getId());
//        TimerJobEntityManager manager = ((ProcessEngineConfigurationImpl)processEngineConfiguration).getTimerJobEntityManager();
//        DelegateExecution execution = delegateTask.getExecution();
//        TimerJobEntity jobEntity = new TimerJobEntityImpl();
//        jobEntity.setExecutionId(delegateTask.getExecutionId());
//        jobEntity.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
//        jobEntity.setProcessInstanceId(delegateTask.getProcessInstanceId());
//        jobEntity.setDuedate(DateUtils.addMinutes(new Date(), 1));
//        jobEntity.setExclusive(true);
//        manager.insert(jobEntity);
    }
}

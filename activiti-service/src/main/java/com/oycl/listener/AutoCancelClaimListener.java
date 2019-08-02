package com.oycl.listener;


import org.flowable.engine.ManagementService;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
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

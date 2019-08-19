package com.oycl.service.impl;


import com.oycl.definition.Constants;
import com.oycl.entity.model.ProcessHistoryModel;
import com.oycl.service.ShowTaskService;
import com.oycl.util.ActivitiUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.RepositoryServiceImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowTaskServiceImpl implements ShowTaskService {

    /** 历史记录相关服务接口 */
    @Autowired
    private HistoryService historyService;

    /** 流程定义和部署相关的存储服务 */
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration ;


    /** 流程图生成器 */
    private ProcessDiagramGenerator processDiagramGenerator = new DefaultProcessDiagramGenerator();

    private static final Logger logger = LoggerFactory.getLogger(ShowTaskServiceImpl.class);

    @Override
    public InputStream ShowImg(String instanceId){

        InputStream imageStream = null;

        logger.info("查看完整流程图！流程实例ID:{}", instanceId);
        if(StringUtils.isBlank(instanceId)){
            return null;
        }

        /**
         * 获取流程实例
         */
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(instanceId).singleResult();
        if(processInstance == null) {
            logger.error("流程实例ID:{}没查询到流程实例！", instanceId);
            return null;
        }

        // 根据流程对象获取流程对象模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        /*
		 *  查看已执行的节点集合
		 *  获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
		 */
        // 构造历史流程查询
        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceId);
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();
        if(historicActivityInstanceList == null || historicActivityInstanceList.size() == 0) {
            logger.info("流程实例ID:{}没有历史节点信息！", instanceId);
            imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png", "宋体", "黑体", "宋体", processEngineConfiguration.getClassLoader(), 1.0, true);
            return imageStream;
        }

        // 已执行的节点ID集合(将historicActivityInstanceList中元素的activityId字段取出封装到executedActivityIdList)
        List<String> executedActivityIdList = historicActivityInstanceList.stream().map(item -> item.getActivityId()).collect(Collectors.toList());

        /*
		 *  获取流程走过的线
		 */
        // 获取流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
        List<String> flowIds = ActivitiUtils.getHighLightedFlows(bpmnModel, processDefinition, historicActivityInstanceList);

        imageStream = processDiagramGenerator.generateDiagram(bpmnModel, "png",executedActivityIdList, flowIds, "宋体", "黑体", "宋体", processEngineConfiguration.getClassLoader(),3.0, true);

        return imageStream;
    }

    @Override
    public List<ProcessHistoryModel> showFullHistory(String instanceId) {

        logger.info("查看完整流程图！流程实例ID:{}", instanceId);
        if(StringUtils.isBlank(instanceId)){
            return null;
        }

        /**
         * 获取流程实例
         */
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(instanceId).singleResult();
        if(processInstance == null) {
            logger.error("流程实例ID:{}没查询到流程实例！", instanceId);
            return null;
        }

        // 构造历史流程查询
        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(instanceId);
        // 查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();
        List<ProcessHistoryModel>  models = new ArrayList<>();
        if(historicActivityInstanceList.size() > 0){
            historicActivityInstanceList.forEach(instance->{
                if(instance.getTaskId() != null){

                    HistoricTaskInstance task =  historyService.createHistoricTaskInstanceQuery().taskId(instance.getTaskId()).includeTaskLocalVariables().singleResult();
                    ProcessHistoryModel model = new ProcessHistoryModel();
                    model.setAssignee(instance.getAssignee());
                    model.setStartTime(instance.getStartTime());
                    model.setEndTime(instance.getEndTime());
                    model.setStatus(instance.getEndTime() != null ? Constants.status.finish.getcode() : Constants.status.approving.getcode());
                    model.setActiveName(instance.getActivityName());
                    model.setProcessInstanceId(instance.getId());
                    List<HistoricIdentityLink> list = historyService.getHistoricIdentityLinksForTask(task.getId());
                    for(HistoricIdentityLink item : list){
                        if(item.getType().equals("candidate")){
                            model.setGroup(item.getGroupId());
                            break;
                        }
                    }
                    if(task != null){
                        model.setParams(task.getTaskLocalVariables());
                    }
                    model.setDeleteReason(instance.getDeleteReason());
                    models.add(model);

                }

            });
        }
        return models;
    }


}

package com.oycl.service.impl;

import com.google.gson.Gson;
import com.oycl.entity.InputParam;
import com.oycl.service.ShowTaskService;
import com.oycl.util.ActivitiUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowTaskServiceImpl implements ShowTaskService {

    /**
     * 流程运行时相关的服务
     */
    @Autowired
    private RuntimeService runtimeService;

    /** 历史记录相关服务接口 */
    @Autowired
    private HistoryService historyService;

    /** 流程定义和部署相关的存储服务 */
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private Gson gson;

    /**
     * 节点任务相关操作接口
     */
    @Autowired
    private TaskService taskService;

    /** 流程图生成器 */
    @Autowired
    private ProcessDiagramGenerator processDiagramGenerator;

    private static final Logger logger = LoggerFactory.getLogger(ShowTaskServiceImpl.class);

    @Override
    public InputStream ShowImg(InputParam inputParam){

        InputStream imageStream = null;

        logger.info("查看完整流程图！流程实例ID:{}", inputParam.getProcessInstanceId());
        if(StringUtils.isBlank(inputParam.getProcessInstanceId())){
            return null;
        }

        /**
         * 获取流程实例
         */
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(inputParam.getProcessInstanceId()).singleResult();
        if(processInstance == null) {
            logger.error("流程实例ID:{}没查询到流程实例！", inputParam.getProcessInstanceId());
            return null;
        }

        // 根据流程对象获取流程对象模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

        /*
		 *  查看已执行的节点集合
		 *  获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
		 */
        // 构造历史流程查询
        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(inputParam.getProcessInstanceId());
        // 查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().asc().list();
        if(historicActivityInstanceList == null || historicActivityInstanceList.size() == 0) {
            logger.info("流程实例ID:{}没有历史节点信息！", inputParam.getProcessInstanceId());
            imageStream = processDiagramGenerator.generateDiagram(bpmnModel, null, null, "宋体", "微软雅黑", "黑体", true, "png");
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

        imageStream = processDiagramGenerator.generateDiagram(bpmnModel, executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", true, "png");

        return imageStream;
    }


}

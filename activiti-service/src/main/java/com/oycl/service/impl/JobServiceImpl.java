package com.oycl.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oycl.definition.Job;
import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;
import com.oycl.entity.TaskModel;
import com.oycl.service.JobService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Service
public class JobServiceImpl implements JobService {
    private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    /**
     * 流程运行时相关的服务
     */
    @Autowired
    private RuntimeService runtimeService;
    /**
     * 节点任务相关操作接口
     */
    @Autowired
    private TaskService taskService;

    @Autowired
    private Gson gson;

    @Autowired
    private RepositoryService repositoryService;




    /**
     * 开启任务
     * @param inputParam 开启任务
     * @return
     */
    @Override
    public OutputParam startJob(InputParam inputParam) {
        OutputParam output = new OutputParam();

        if(StringUtils.isEmpty(inputParam.getJobId())){
            output.setResult("fail");
            output.setMessage("must required job id ");
            return output;
        }
        logger.info("开启流程..."+ Job.JOB.getJobKey(inputParam.getJobId()));

        String json = inputParam.getJsonParam();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(Job.JOB.getJobKey(inputParam.getJobId())
                ,inputParam.getBusinessKey()
                ,map);

        // 获取流程启动产生的taskId
        Task task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("taskId", task.getId());
        jsonObject.addProperty("processInstanceId",task.getProcessInstanceId());

        output.setResultStrObj(gson.toJson(jsonObject));


        logger.info("启动流程实例成功:{}", instance);
        logger.info("流程实例ID:{}", instance.getId());
        logger.info("流程定义ID:{}", instance.getProcessInstanceId());

        return output;
    }

    /**
     * 取得任务一览（个人）
     *
     * @param group
     * @return
     */
    @Override
    public String showTask(String userId,String group) {
        TaskQuery taskQuery = null;
        if(userId != null){
            taskQuery = taskService.createTaskQuery().taskAssignee(userId);
        }
        if(group != null){
            taskQuery = taskService.createTaskQuery().taskCandidateGroup(group);
        }
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<TaskModel> resultList = new ArrayList<>();

        list.forEach(item -> {
            //取得流程变量
            Map<String, Object> map =  taskService.getVariables(item.getId());
           // Map<String, Object> map =  runtimeService.getVariables(item.getExecutionId());
            TaskModel entity = new TaskModel();
            entity.setAssignee(item.getAssignee());
            entity.setCreateTime(item.getCreateTime().toString());
            entity.setDefinitionId(item.getProcessDefinitionId());
            entity.setInstanceId(item.getProcessInstanceId());
            entity.setTaskId(item.getId());
            entity.setName(item.getName());
            entity.setParams(map);

            resultList.add(entity);
        });

        return gson.toJson(resultList);
    }

    /**
     * 认领任务
     * @param inputParam
     * @return
     */
    @Override
    public OutputParam claim(InputParam inputParam){
        OutputParam output = new OutputParam();
        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).singleResult();
        if (task == null) {
            output.setResult("fail");
            output.setMessage("must required task id ");
            logger.info("审核任务ID:{}查询到任务为空！", inputParam.getTaskId());
            return output;
        }
        taskService.claim(inputParam.getTaskId(), inputParam.getUserId());
        return output;
    }

    /**
     * 分支走向
     * @param inputParam
     * @return
     */
    @Override
    public OutputParam complete(InputParam inputParam){
        OutputParam output = new OutputParam();
        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).singleResult();
        if (task == null) {
            output.setResult("fail");
            output.setMessage("must required task id ");
            logger.info("审核任务ID:{}查询到任务为空！", inputParam.getTaskId());
            return output;
        }

        //设置局部动作变量
        String actionJson = inputParam.getActionParam();
        if(actionJson != null && !actionJson.equals("")){
            Map<String, Object> map = gson.fromJson(actionJson, Map.class);
            taskService.setVariablesLocal(task.getId(),map);
        }

        //分支走向
        if(inputParam.getJsonParam() != null && !inputParam.getJsonParam().equals("")){
            Map<String, Object> map = gson.fromJson(inputParam.getJsonParam(), Map.class);
            taskService.setVariables(inputParam.getTaskId(), map);
        }

        taskService.complete(inputParam.getTaskId());

        return output;
    }


    /**
     * 部署新流程
     * @param fileName
     */
    public OutputParam deploymentZip(String fileName){
        OutputParam output = new OutputParam();
        try {
            URL uri =  this.getClass().getClassLoader().getResource("/processes/"+fileName);
            if(uri != null){
                output.setResult("fail");
                output.setMessage("zip file isn`t exist ");
                logger.info("部署流程:{}文件不存在！", fileName);
            }
            ZipInputStream inputStream = new ZipInputStream(uri.openStream());
            repositoryService.createDeployment().addZipInputStream(inputStream).deploy();
        } catch (IOException e) {
            output.setResult("fail");
            output.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return output;
    }

    /**
     * 删除流程定义
     * @param inputParam
     */
    public void deleteProcess(InputParam inputParam){
      ProcessDefinition definition =  repositoryService.createProcessDefinitionQuery().processDefinitionKey(Job.JOB.getJobKey(inputParam.getJobId())).singleResult();
      repositoryService.deleteDeployment(definition.getDeploymentId());
      //级联删除
        //repositoryService.deleteDeployment(definition.getDeploymentId(), true);
    }








}

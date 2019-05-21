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
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(Job.JOB.getJobKey(inputParam.getJobId()), map);

//        // 获取流程启动产生的taskId
//        Task task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
//
//        taskService.claim(task.getId(), inputParam.getUserId());
//
//        taskService.complete(task.getId());
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("taskId", task.getId());
//        jsonObject.addProperty("processInstanceId",task.getProcessInstanceId());
//
//        output.setResultStrObj(gson.toJson(jsonObject));


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
            if(taskQuery == null){
                taskQuery = taskService.createTaskQuery().taskCandidateGroup(group);
            }else{
                taskQuery =  taskQuery.taskCandidateGroup(group);
            }
        }
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<TaskModel> resultList = new ArrayList<>();

        list.forEach(item -> {
            //取得流程变量
            Map<String, Object> map =  runtimeService.getVariables(item.getExecutionId());
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
     * 分支走向
     * @param inputParam
     * @return
     */
    @Override
    public OutputParam approve(InputParam inputParam){
        OutputParam output = new OutputParam();
        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).singleResult();
        if (task == null) {
            output.setResult("fail");
            output.setMessage("must required task id ");
            logger.info("审核任务ID:{}查询到任务为空！", inputParam.getTaskId());
            return output;
        }

        String json = inputParam.getJsonParam();
        //分支走向
        if(inputParam.getJsonParam() != null && !inputParam.getJsonParam().equals("")){
            Map<String, Object> map = gson.fromJson(json, Map.class);
            taskService.complete(inputParam.getTaskId(), map);
        }else{
            //非分支，单路线走向
            taskService.complete(inputParam.getTaskId());
        }

        return output;
    }

    /**
     * 部署新流程
     * @param file
     * @param fileName
     */
    public void deployment(MultipartFile file, String fileName){
        try {
            repositoryService.createDeployment().addInputStream(fileName, file.getInputStream()).deploy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}

package com.oycl.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;
import com.oycl.entity.ProcessModel;
import com.oycl.entity.TaskModel;
import com.oycl.service.JobService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    @Autowired
    private HistoryService historyService;



    /**
     * 开启任务
     *
     * @param inputParam 开启任务
     * @return
     */
    @Override
    public OutputParam startJob(InputParam inputParam) {
        OutputParam output = new OutputParam();

        if (StringUtils.isEmpty(inputParam.getProcessDefinitionKey())) {
            output.setResult("fail");
            output.setMessage("must required job id ");
            return output;
        }
        logger.info("开启流程..." + inputParam.getProcessDefinitionKey());

        String json = inputParam.getJsonParam();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        map.put("applyUserId", inputParam.getUserId());
        Authentication.setAuthenticatedUserId(inputParam.getUserId());

        ProcessInstance instance = null;
        //使用流程定义的DefinitionId启动流程实例,安装定义id中的版本号启动任务
        if(StringUtils.isNotEmpty(inputParam.getProcessDefinitionId())){
            instance = runtimeService.startProcessInstanceById(inputParam.getProcessDefinitionId()
                    , inputParam.getBusinessKey()
                    , map);
            //使用流程定义的key启动流程实例，使用key值启动，默认是按照最新版本的流程定义启动
        }else if(StringUtils.isNotEmpty(inputParam.getProcessDefinitionKey())){
            instance = runtimeService.startProcessInstanceByKey(inputParam.getProcessDefinitionKey()
                    , inputParam.getBusinessKey()
                    , map);
        }
        // 获取流程启动产生的taskId
        Task task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("taskId", task.getId());
        jsonObject.addProperty("processInstanceId", task.getProcessInstanceId());

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
    public String showTask(String userId, String group) {
        TaskQuery taskQuery = null;
        if (userId != null) {
            taskQuery = taskService.createTaskQuery().taskAssignee(userId);
        }

        if (group != null) {
            taskQuery = taskService.createTaskQuery().taskCandidateGroup(group).includeProcessVariables();
        }
        List<Task> list = taskQuery.list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<TaskModel> resultList = new ArrayList<>();

        list.forEach(item -> {
            //取得流程变量
            // Map<String, Object> map =  runtimeService.getVariables(item.getExecutionId());
            TaskModel entity = new TaskModel();
            entity.setAssignee(item.getAssignee());
            entity.setCreateTime(item.getCreateTime());
            entity.setTaskId(item.getId());

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(item.getProcessInstanceId()).singleResult();
            if (processInstance != null) {
                entity.setProcessDefinitionName(processInstance.getProcessDefinitionName());
            }
            entity.setTaskName(item.getName());
            entity.setParams(item.getProcessVariables());
            entity.setInstanceId(item.getProcessInstanceId());
            resultList.add(entity);
        });

        return gson.toJson(resultList);
    }

    /**
     * 取得审批任务详情（个人）
     *
     * @param taskId
     * @return
     */
    @Override
    public OutputParam showTaskDetail(String taskId) {
        OutputParam output = new OutputParam();
        Task task = taskService.createTaskQuery().taskId(taskId).includeProcessVariables().singleResult();
        if (task == null) {
            output.setResult("fail");
            output.setMessage("must required task id ");
            logger.info("审核任务ID:{}查询到任务为空！", taskId);
            return output;
        }

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .orderByHistoricTaskInstanceStartTime()
                .desc().list();

        TaskModel entity = new TaskModel();
        entity.setCreateTime(task.getCreateTime());
        entity.setTaskId(task.getId());

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance != null) {
            entity.setProcessDefinitionName(processInstance.getProcessDefinitionName());
        }
        entity.setTaskName(task.getName());
        entity.setParams(task.getProcessVariables());
        entity.setInstanceId(task.getProcessInstanceId());
        Map<String, Object> comments = new HashMap<>();
        historicTaskInstances.forEach(item->{
            if(item.getEndTime()!= null){
                comments.put(item.getAssignee(), taskService.getTaskComments(item.getId()).stream().map(comment->comment.getFullMessage()).collect(Collectors.toList()));
            }
        });
        entity.setComments(comments);
        output.setResultStrObj(gson.toJson(entity));
        return output;
    }

    /**
     * 认领任务
     *
     * @param inputParam
     * @return
     */
    @Override
    public OutputParam claim(InputParam inputParam) {
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

    @Override
    public OutputParam cancelClaim(InputParam inputParam) {
        OutputParam output = new OutputParam();
        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).singleResult();
        if (task == null) {
            output.setResult("fail");
            output.setMessage("must required task id ");
            logger.info("审核任务ID:{}查询到任务为空！", inputParam.getTaskId());
            return output;
        }
        taskService.unclaim(inputParam.getTaskId());
        return output;
    }

    @Override
    public String showLatestProcessList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().active().latestVersion().list();
        return gson.toJson(setProcessMode(list));
    }

    @Override
    public String showAllProcessList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().active().list();
        return gson.toJson(setProcessMode(list));
    }

    private List<ProcessModel> setProcessMode(List<ProcessDefinition> list){
        List<ProcessModel> models = new ArrayList<>();
        list.forEach(item -> {
            ProcessModel model = new ProcessModel();
            model.setProcessDefinitionKey(item.getKey());
            model.setDescription(item.getDescription());
            model.setVersion(item.getVersion());
            model.setProcessDefinitionName(item.getName());
            models.add(model);
        });
        return models;
    }


    /**
     * 分支走向
     *
     * @param inputParam
     * @return
     */
    @Override
    public OutputParam complete(InputParam inputParam) {
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
        if (actionJson != null && !actionJson.equals("")) {
            Map<String, Object> map = gson.fromJson(actionJson, Map.class);
            taskService.setVariablesLocal(task.getId(), map);
        }

        //修改全局变量
        if (inputParam.getJsonParam() != null && !inputParam.getJsonParam().equals("")) {
            Map<String, Object> map = gson.fromJson(inputParam.getJsonParam(), Map.class);
            taskService.setVariables(inputParam.getTaskId(), map);
        }

        // 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
        ///批注人的名称  一定要写，不然查看的时候不知道人物信息

        // 添加批注信息
        //comment为批注内容
        if (StringUtils.isNoneEmpty(inputParam.getComment())) {
            Authentication.setAuthenticatedUserId(inputParam.getUserId());
            taskService.addComment(task.getId(), null, inputParam.getComment());
        }

        taskService.complete(inputParam.getTaskId());

        return output;
    }


    /**
     * 部署新流程
     *
     * @param file
     */
    @Override
    @Transactional
    public OutputParam deployment(MultipartFile file) {
        OutputParam output = new OutputParam();
        ZipInputStream inputStream = null;
        try {
            if (!StringUtils.endsWith(file.getOriginalFilename(), ".xml")) {
                output.setResult("fail");
                output.setMessage("zip file is illegal ");
                logger.info("部署流程:{}文件不合法！", file.getOriginalFilename());
                return output;
            }
            repositoryService.createDeployment().addInputStream(file.getOriginalFilename(), file.getInputStream()).deploy();
        } catch (IOException e) {
            output.setResult("fail");
            output.setMessage(e.getMessage());
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output;
    }

    /**
     * 删除流程定义
     *
     * @param inputParam
     */
    public void deleteProcess(InputParam inputParam) {
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(inputParam.getProcessDefinitionKey()).singleResult();
        repositoryService.deleteDeployment(definition.getDeploymentId());
        //级联删除
        //repositoryService.deleteDeployment(definition.getDeploymentId(), true);
    }

    /**
     * 删除流程
     *
     * @param inputParam
     */
    @Override
    public OutputParam stopProcessInstance(InputParam inputParam) {
        OutputParam output = new OutputParam();
        if(0 == runtimeService.createProcessInstanceQuery().processInstanceId(inputParam.getProcessInstanceId()).count()){
            output.setResult("fail");
            output.setMessage("ProcessInstance is not exist ");
        }
        runtimeService.deleteProcessInstance(inputParam.getProcessInstanceId(),inputParam.getComment());
        return output;
    }

    /**
     * 显示用户所有的流程情况
     *
     * @param inputParam
     * @return
     */
    @Override
    public String showUserProcess(InputParam inputParam) {
        List<ProcessModel> models = new ArrayList<>();
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().involvedUser(inputParam.getUserId()).list();
        list.forEach(process -> {
            ProcessModel model = new ProcessModel();
            model.setProcessDefinitionName(process.getProcessDefinitionName());
            model.setDescription(process.getDescription());
            model.setStartTime(process.getStartTime());
            model.setEndTime(process.getEndTime());
            model.setStatus(process.getEndTime() != null ? "已完成" : "审批中");
            model.setActiveName(getCurrentActivePoint(process.getId()));
            model.setVersion(process.getProcessDefinitionVersion());
            model.setProcessInstanceId(process.getId());
            models.add(model);
        });

        return gson.toJson(models);
    }

    @Override
    public String showCurrentPoint(InputParam inputParam) {

        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(inputParam.getProcessInstanceId()).activityType("userTask");

        // 查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().desc().listPage(0, 1);

        if (historicActivityInstanceList.size() == 0) {
            return "不存在";
        }
        HistoricActivityInstance hsInstance = historicActivityInstanceList.get(0);
        if (hsInstance.getEndTime() != null) {
            return "已完成";
        }

        return gson.toJson(hsInstance);

    }

    private String getCurrentActivePoint(String processInstanceId) {

        HistoricActivityInstanceQuery historyInstanceQuery = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask");

        // 查询历史节点
        List<HistoricActivityInstance> historicActivityInstanceList = historyInstanceQuery.orderByHistoricActivityInstanceStartTime().desc().listPage(0, 1);

        if (historicActivityInstanceList.size() == 0) {
            return null;
        }
        HistoricActivityInstance hsInstance = historicActivityInstanceList.get(0);
        if (hsInstance.getEndTime() != null) {
            return null;
        }

        return hsInstance.getActivityName();

    }








}

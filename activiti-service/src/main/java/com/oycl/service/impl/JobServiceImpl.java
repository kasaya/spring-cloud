package com.oycl.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;
import com.oycl.entity.ProcessModel;
import com.oycl.entity.TaskModel;
import com.oycl.exception.ActivityException;
import com.oycl.exception.BaseException;
import com.oycl.service.JobService;
import org.apache.commons.lang3.StringUtils;

import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricActivityInstanceQuery;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
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
    @Transactional(rollbackFor = Exception.class)
    public JsonObject startJob(InputParam inputParam) throws Exception {

        if (StringUtils.isEmpty(inputParam.getProcessDefinitionId()) && StringUtils.isEmpty(inputParam.getProcessDefinitionKey())) {
            throw new ActivityException("");
        }

        logger.info("开启流程..." + inputParam.getProcessDefinitionKey());

        String json = inputParam.getGlobalParam();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        map.put("applyUserId", inputParam.getUserId());
        Authentication.setAuthenticatedUserId(inputParam.getUserId());

        ProcessInstance instance = null;
        try {
            //使用流程定义的DefinitionId启动流程实例,安装定义id中的版本号启动任务
            if (StringUtils.isNotEmpty(inputParam.getProcessDefinitionId())) {
                instance = runtimeService.startProcessInstanceById(inputParam.getProcessDefinitionId()
                        , inputParam.getBusinessKey()
                        , map);
                //使用流程定义的key启动流程实例，使用key值启动，默认是按照最新版本的流程定义启动
            } else if (StringUtils.isNotEmpty(inputParam.getProcessDefinitionKey())) {
                instance = runtimeService.startProcessInstanceByKey(inputParam.getProcessDefinitionKey()
                        , inputParam.getBusinessKey()
                        , map);
            }
        } catch (Exception e) {
            //流程定义不存在
            throw new ActivityException("");
        }

        // 获取流程启动产生的taskId
        Task task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("taskId", task.getId());
        jsonObject.addProperty("processInstanceId", task.getProcessInstanceId());


        logger.info("启动流程实例成功:{}", instance);
        logger.info("流程实例ID:{}", instance.getId());
        logger.info("流程定义ID:{}", instance.getProcessInstanceId());


        return jsonObject;
    }

    /**
     * 取得任务一览（个人）
     *
     * @param group
     * @return
     */
    @Override
    public  List<TaskModel> showTask(String userId, String group)throws Exception {


        if (StringUtils.isEmpty(userId) && StringUtils.isEmpty(group)) {
            throw new ActivityException("");
        }
        TaskQuery taskQuery = null;
        if (userId != null) {
            //取得经办人是自己的任务,或者是被委派的任务
            taskQuery = taskService.createTaskQuery().taskAssignee(userId).or().taskDelegationState(DelegationState.PENDING).taskAssignee(userId).endOr();
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
            TaskModel entity = new TaskModel();
            entity.setAssignee(item.getAssignee());
            entity.setCreateTime(item.getCreateTime());
            entity.setTaskId(item.getId());
            entity.setOwner(item.getOwner());
            entity.setClaimTime(item.getClaimTime());
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(item.getProcessInstanceId()).singleResult();
            if (processInstance != null) {
                entity.setProcessDefinitionName(processInstance.getProcessDefinitionName());
            }
            entity.setTaskName(item.getName());
            entity.setParams(item.getProcessVariables());
            entity.setInstanceId(item.getProcessInstanceId());
            resultList.add(entity);
        });

        return resultList;
    }

    /**
     * 取得审批任务详情（个人）
     *
     * @param taskId
     * @return
     */
    @Override
    public TaskModel showTaskDetail(String taskId) throws Exception {
        Task task = taskService.createTaskQuery().taskId(taskId).includeProcessVariables().singleResult();
        if (task == null) {
            throw new ActivityException("");
        }
        //取得任务历史
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .orderByHistoricTaskInstanceStartTime()
                .desc().list();

        TaskModel entity = new TaskModel();
        entity.setCreateTime(task.getCreateTime());
        entity.setTaskId(task.getId());
        entity.setAssignee(task.getAssignee());
        entity.setOwner(task.getOwner());
        entity.setClaimTime(task.getClaimTime());
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        if (processInstance != null) {
            entity.setProcessDefinitionName(processInstance.getProcessDefinitionName());
        }
        entity.setTaskName(task.getName());
        entity.setParams(task.getProcessVariables());
        entity.setInstanceId(task.getProcessInstanceId());
        List<Object> comments = new ArrayList<>();
        //取得审批内容
        historicTaskInstances.forEach(item -> {
            if (item.getEndTime() != null) {
                comments.add(taskService.getTaskComments(item.getId()));
            }
        });
        entity.setComments(comments);
        return entity;
    }

    /**
     * 认领任务
     *
     * @param inputParam
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean claim(InputParam inputParam) throws Exception {
        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).singleResult();
        if (task == null) {
            throw new ActivityException("");
        }
        taskService.claim(inputParam.getTaskId(), inputParam.getUserId());
        return true;
    }

    @Override
    @Transactional
    public boolean cancelClaim(InputParam inputParam) throws Exception{
        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).singleResult();
        if (task == null) {
            throw new ActivityException("");
        }
        taskService.unclaim(inputParam.getTaskId());
        return true;
    }

    @Override
    public List<ProcessModel> showLatestProcessList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().active().latestVersion().list();
        return setProcessMode(list);
    }

    @Override
    public List<ProcessModel> showAllProcessList() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().active().list();
        return setProcessMode(list);
    }

    private List<ProcessModel> setProcessMode(List<ProcessDefinition> list) {
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
    @Transactional(rollbackFor = Exception.class)
    public boolean complete(InputParam inputParam) throws Exception {
        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).singleResult();
        if (task == null) {
            throw new ActivityException("");
        }

        //设置局部动作变量
        if (inputParam.getLocalParam() != null && !inputParam.getLocalParam().equals("")) {
            Map<String, Object> map = gson.fromJson(inputParam.getLocalParam(), Map.class);
            taskService.setVariablesLocal(task.getId(), map);
        }

        //修改全局变量
        if (inputParam.getGlobalParam() != null && !inputParam.getGlobalParam().equals("")) {
            Map<String, Object> map = gson.fromJson(inputParam.getGlobalParam(), Map.class);
            taskService.setVariables(inputParam.getTaskId(), map);
        }

        // 添加批注信息
        //comment为批注内容
        synchronized (this) {
            if (StringUtils.isNotEmpty(inputParam.getComment())) {
                // 由于流程用户上下文对象是线程独立的，所以要在需要的位置设置，要保证设置和获取操作在同一个线程中
                ///批注人的名称  一定要写，不然查看的时候不知道人物信息
                Authentication.setAuthenticatedUserId(inputParam.getUserId());
                taskService.addComment(task.getId(), null, inputParam.getComment());
            }
        }

        if(StringUtils.isNotEmpty(task.getOwner()) && !task.getOwner().equals(inputParam.getUserId())){
            taskService.resolveTask(inputParam.getTaskId());
        }else{
            taskService.complete(inputParam.getTaskId());
        }

        return true;
    }


    /**
     * TODO 部署新流程
     *
     * @param files
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deployment(MultipartFile[] files) throws Exception {
        if(files == null || files.length <= 0){
            throw new ActivityException("");
        }else{
            for(MultipartFile file: files){
                if (!StringUtils.endsWith(file.getOriginalFilename(), "xml")) {
                    throw new ActivityException("");
                }
                try {
                    repositoryService.createDeployment().addInputStream(file.getOriginalFilename(), file.getInputStream()).deploy();
                } catch (IOException e) {
                    throw new BaseException("");
                }
            }
        }
        return true;
    }

    /**
     * 删除流程定义
     *
     * @param inputParam
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcess(InputParam inputParam) throws Exception{
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
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteProcessInstance(InputParam inputParam) throws Exception{
        if (0 == runtimeService.createProcessInstanceQuery().processInstanceId(inputParam.getProcessInstanceId()).count()) {
            throw new ActivityException("");
        }
        runtimeService.deleteProcessInstance(inputParam.getProcessInstanceId(), inputParam.getComment());
        return true;
    }

    /**
     * 显示用户所有的流程情况
     *
     * @param inputParam
     * @return
     */
    @Override
    public List<ProcessModel> showUserProcess(InputParam inputParam) {
        List<ProcessModel> models = new ArrayList<>();
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().involvedUser(inputParam.getUserId()).list();
        list.forEach(process -> {
            ProcessModel model = new ProcessModel();
            model.setProcessDefinitionName(process.getProcessDefinitionName());
            model.setDescription(process.getDescription());
            model.setStartTime(process.getStartTime());
            model.setEndTime(process.getEndTime());
            model.setStatus(process.getEndTime() != null ? "finish" : "approving");
            model.setActiveName(getCurrentActivePoint(process.getId()));
            model.setVersion(process.getProcessDefinitionVersion());
            model.setProcessInstanceId(process.getId());
            models.add(model);
        });

        return models;
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

    /**
     * 任务委派
     * @param inputParam
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delegateTask(InputParam inputParam) throws Exception{
        //取得当前需要委派的任务

        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).taskAssignee(inputParam.getUserId()).singleResult();

        if (task == null) {
            throw new ActivityException("001");
        }
        //委派任务
        taskService.delegateTask(task.getId(), inputParam.getTargetUserId());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean turnTask(InputParam inputParam) throws Exception{
        //取得当前需要转派的任务

        Task task = taskService.createTaskQuery().taskId(inputParam.getTaskId()).taskAssignee(inputParam.getUserId()).singleResult();

        if (task == null) {
            throw new ActivityException("001");
        }
        //设置转派的人
        taskService.setAssignee(inputParam.getTaskId(), inputParam.getTargetUserId());
        return true ;
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

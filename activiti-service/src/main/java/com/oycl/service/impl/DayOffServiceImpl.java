package com.oycl.service.impl;

import com.google.gson.Gson;
import com.oycl.entity.DayOffInput;
import com.oycl.entity.DayOffOutPut;
import com.oycl.entity.TaskModel;
import com.oycl.service.DayOffService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DayOffServiceImpl implements DayOffService {

    private final static Logger logger = LoggerFactory.getLogger(DayOffServiceImpl.class);

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

    @Override
    public DayOffOutPut startDayoff(DayOffInput input) {
        // xml中定义的ID
        String instanceKey = "day_off";
        logger.info("开启请假流程...");

        Map<String, Object> map = new HashMap<>();
        map.put("param", input);
        //使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(instanceKey, map);

        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();

        taskService.claim(task.getId(), input.getUserId());

        Map<String, Object> values = new HashMap<>();

        taskService.complete(task.getId());

        logger.info("启动流程实例成功:{}", instance);
        logger.info("流程实例ID:{}", instance.getId());
        logger.info("流程定义ID:{}", instance.getProcessDefinitionId());

        DayOffOutPut output = new DayOffOutPut();
        output.setTaskId(task.getId());
        output.setProcessId(instance.getId());
        return output;
    }

    /**
     * 查看任务一览
     *
     * @param group
     * @return
     */
    @Override
    public String showTask(String group) {
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup(group).orderByTaskCreateTime().desc().list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<TaskModel> resultList = new ArrayList<>();

        list.forEach(item -> {
            Map<String, Object> map =  taskService.getVariables(item.getId());
            TaskModel entity = new TaskModel();
            entity.setAssignee(item.getAssignee());
            entity.setCreateTime(item.getCreateTime().toString());
            entity.setDefinitionId(item.getProcessDefinitionId());
            entity.setInstanceId(item.getProcessInstanceId());
            entity.setTaskId(item.getId());
            entity.setName(item.getName());
            entity.setParam(map.get("param")== null?null:gson.toJson(map.get("param")));

            resultList.add(entity);
        });

        return gson.toJson(resultList);
    }

    /**
     * mg 审批
     * @param input
     * @return
     */
    @Override
    public String mgApprove(DayOffInput input) {

        Task task = taskService.createTaskQuery().taskId(input.getTaskId()).singleResult();
        if (task == null) {
            logger.info("审核任务ID:{}查询到任务为空！", input.getTaskId());
            return "fail";
        }
        /**
         * 设置局部变量参数，完成任务
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("approve", "1".equals(input.getApprove()) ? false : true);
        taskService.complete(input.getTaskId(), map);
        return "success";
    }

    @Override
    public DayOffOutPut employeeApply(DayOffInput input) {
        DayOffOutPut result = new DayOffOutPut();
//        Task task = taskService.createTaskQuery().processInstanceId(input.getProcessId()).singleResult();
//        if (task == null) {
//            logger.info("任务ID:{}查询到任务为空！", input.getTaskId());
//            result.setResultStatus("fail");
//            return result;
//        }
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("param", input);
//        taskService.complete(task.getId(), map);
//
//        result.setResultStatus("success");
        return result;
    }


}

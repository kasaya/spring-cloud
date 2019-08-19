package com.oycl.service;


import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;
import com.oycl.entity.model.ProcessModel;
import com.oycl.entity.model.TaskModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JobService {

    /**
     * 启动任务
     * @param inputParam
     * @return
     */
    OutputParam startJob(InputParam inputParam) throws Exception;

    /**
     * 显示用户组任务
     * @param inputParam
     * @return
     */
    List<TaskModel> showGroupTask(InputParam inputParam);

    /**
     * 显示用户任务
     * @param inputParam
     * @return
     */
    List<TaskModel> showUserTask(InputParam inputParam);

    /**
     * 显示任务详细
     * @param taskId
     * @return
     */
    TaskModel showTaskDetail(String taskId) throws Exception;

    /**
     * 完成任务
     * @param inputParam
     * @return
     */
    OutputParam complete(InputParam inputParam) throws Exception;

    /**
     * 认领任务
     * @param inputParam
     * @return
     */
    boolean claim(InputParam inputParam) throws Exception;

    /**
     * 显示最新的流程定义列表
     * @return
     */
    List<ProcessModel> showLatestProcessList();

    /**
     * 显示全部的流程定义列表
     * @return
     */
    List<ProcessModel> showAllProcessList();

    /**
     * 显示用户申请的流程
     * @param inputParam
     * @return
     */
    List<ProcessModel> showUserProcess(InputParam inputParam);

    /**
     * 取消认领任务
     * @param inputParam
     * @return
     */
    boolean cancelClaim(InputParam inputParam) throws Exception;

    /**
     * 显示当前节点
     * @param inputParam
     * @return
     */
    String showCurrentPoint(InputParam inputParam )throws Exception;

    /**
     * 发布流程定义
     * @param file
     * @return
     */
    boolean deployment(MultipartFile[] file) throws Exception;

    /**
     * 删除流程实例
     * @param inputParam
     * @return
     */
    OutputParam deleteProcessInstance(InputParam inputParam) throws Exception;


    /**
     * 任务委派
     * @param inputParam
     */
    boolean delegateTask(InputParam inputParam) throws Exception;


    /**
     * 任务转派
     * @param inputParam
     * @return
     */
    boolean turnTask(InputParam inputParam) throws Exception;
}

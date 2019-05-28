package com.oycl.service;


import com.oycl.entity.InputParam;
import com.oycl.entity.OutputParam;
import org.springframework.web.multipart.MultipartFile;

public interface JobService {

    /**
     * 启动任务
     * @param inputParam
     * @return
     */
    OutputParam startJob(InputParam inputParam);

    /**
     * 显示用户/用户组任务
     * @param user
     * @param group
     * @return
     */
    String showTask(String user,String group);

    /**
     * 显示任务详细
     * @param taskId
     * @return
     */
    OutputParam showTaskDetail(String taskId);

    /**
     * 完成任务
     * @param inputParam
     * @return
     */
    OutputParam complete(InputParam inputParam);

    /**
     * 认领任务
     * @param inputParam
     * @return
     */
    OutputParam claim(InputParam inputParam);

    /**
     * 显示最新的流程定义列表
     * @return
     */
    String showLatestProcessList();

    /**
     * 显示全部的流程定义列表
     * @return
     */
    String showAllProcessList();

    /**
     * 显示用户申请的流程
     * @param inputParam
     * @return
     */
    String showUserProcess(InputParam inputParam);

    /**
     * 取消认领任务
     * @param inputParam
     * @return
     */
    OutputParam cancelClaim( InputParam inputParam);

    /**
     * 显示当前节点
     * @param inputParam
     * @return
     */
    String showCurrentPoint(InputParam inputParam);

    /**
     * 发布流程定义
     * @param file
     * @return
     */
    OutputParam deployment(MultipartFile file);

    /**
     * 取消流程实例
     * @param inputParam
     * @return
     */
    OutputParam stopProcessInstance(InputParam inputParam);
}

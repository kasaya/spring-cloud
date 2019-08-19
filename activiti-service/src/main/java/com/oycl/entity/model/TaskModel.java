package com.oycl.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class TaskModel {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 认领时间
     */
    private Date claimTime;
    /**
     * 办理人
     */
    private String assignee;
    /**
     * 流程实例ID
     */
    private String processInstanceId;
    /**
     * 流程定义名称
     */
    private String processDefinitionName;
    /**
     * 任务委托人
     */
    private String owner;
    /**
     * 流程节点定义
     */
    private String group;

    /**
     * 流程节点定义
     */
    private String systemName;

    /**
     *  业务KEY
     */
    private String businessKey;
    /**
     * 审批意见
     */
    private List<Object> comments;

    /**用户参数*/
    private Map<String, Object> params;



}

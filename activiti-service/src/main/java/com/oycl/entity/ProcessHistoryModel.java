package com.oycl.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public class ProcessHistoryModel {

    private String jobId;
    private String processDefinitionName;
    private String description;
    private int version;

    private String assignee;

    private String group;
    /**
     * 当前活动节点名称
     */
    private String activeName;

    /**
     * 状态
     */
    private String status;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 流程实例ID
     */
    private String processInstanceId;

    /**用户参数*/
    private Map<String, Object> params;

    /**删除原因*/
    private String deleteReason;






}

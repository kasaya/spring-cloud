package com.oycl.entity.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class ProcessModel {

    /**
     * 流程定义Key
     */
    private String processDefinitionKey;
    /**
     * 流程定义名称
     */
    private String processDefinitionName;
    /**
     * 流程描述
     */
    private String description;
    /**
     * 版本
     */
    private int version;

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

    /**
     * 结束动作ID
     */
    private String endActId;






}

package com.oycl.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProcessModel {

    private String processDefinitionKey;
    private String processDefinitionName;
    private String description;
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






}

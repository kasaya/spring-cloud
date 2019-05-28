package com.oycl.entity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TaskModel {

    private String taskId;
    private String taskName;
    private Date createTime;
    private String assignee;
    private String instanceId;
    private String processDefinitionName;
    private Map<String,Object> comments;

    /**用户参数*/
    private Map<String, Object> params;



}